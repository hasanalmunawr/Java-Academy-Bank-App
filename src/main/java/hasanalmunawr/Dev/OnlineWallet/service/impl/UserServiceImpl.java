package hasanalmunawr.Dev.OnlineWallet.service.impl;

import hasanalmunawr.Dev.OnlineWallet.dto.AccountInfo;
import hasanalmunawr.Dev.OnlineWallet.dto.request.ChangeRequest;
import hasanalmunawr.Dev.OnlineWallet.dto.request.LoginRequest;
import hasanalmunawr.Dev.OnlineWallet.dto.request.RegisterRequest;
import hasanalmunawr.Dev.OnlineWallet.dto.response.AuthResponse;
import hasanalmunawr.Dev.OnlineWallet.dto.response.RegisterResponse;
import hasanalmunawr.Dev.OnlineWallet.exception.*;
import hasanalmunawr.Dev.OnlineWallet.repository.TokenCodeRepository;
import hasanalmunawr.Dev.OnlineWallet.repository.TokenRepository;
import hasanalmunawr.Dev.OnlineWallet.repository.UserRepository;
import hasanalmunawr.Dev.OnlineWallet.security.JwtService;
import hasanalmunawr.Dev.OnlineWallet.service.AccountService;
import hasanalmunawr.Dev.OnlineWallet.service.EmailService;
import hasanalmunawr.Dev.OnlineWallet.service.UserService;
import hasanalmunawr.Dev.OnlineWallet.entity.*;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import static hasanalmunawr.Dev.OnlineWallet.utils.EmailUtils.ACTIVATION_ACCOUNT;
import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenCodeRepository codeRepository;
    private final EmailService emailService;


    @Override
    public RegisterResponse register(RegisterRequest request) {
        log.info("Register request: {}", request);
        try {
            var userEmail = userRepository.findByEmail(request.getEmail());
            if (userEmail.isPresent()) {
                throw new UserAlreadyExistException("User With "+request.getEmail()+" Already Exist");
            }

            UserEntity user = UserEntity.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .primaryAccount(accountService.createPrimaryAccount())
                    .phone(request.getPhone())
                    .enabled(false)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .build();

            UserEntity savedUser = userRepository.save(user);
            sendValidationEmail(savedUser);

            return RegisterResponse.builder()
                    .accountInfo(convertUserToACI(user))
                    .build();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @Override
    public AuthResponse authentication(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                        )
                );
            var userLogin = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(AccountNotFoundException::new);

            String generatedToken = jwtService.generateToken(userLogin);
            long accessExpiration = jwtService.getJwtExpiration();

            savedUserToken(generatedToken, userLogin);
            return AuthResponse.builder()
                    .username(userLogin.getEmail())
                    .accessTokenExpiry((int) accessExpiration)
                    .accessToken(generatedToken)
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Email Not Found");
        }
    }

    @Transactional
    @Override
    public void activateAccount(String tokenCode) {
        TokenCodeEntity codeEntity = codeRepository.findByToken(tokenCode)
                .orElseThrow(() -> new EntityNotFound("Token Not Found"));

        if (LocalDateTime.now().isAfter(codeEntity.getExpiresAt())) {
            throw new ActivationTokenException("Activation token has expired. A new token has been send to tha same email " +
                    "address");
        }

        var userEntity = userRepository.findById(codeEntity.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User " +
                        "Not Found"));
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
    }



    @Transactional
    @Override
    public void changePassword(UserEntity currentUser, ChangeRequest request) {

        if (passwordEncoder.matches(currentUser.getPassword(), request.oldPassword())) {
            currentUser.setPassword(request.newPassword());

            userRepository.save(currentUser);
        } else {
            throw new OperationNotPermittedException("Password Not Permitted");
        }
    }


    private void savedUserToken(String jwtToken, UserEntity user) {
        Token token = Token.builder()
                .token(jwtToken)
                .user(user)
                .tokenType(TokenType.BEARER)
                .isRevoked(false)
                .isExpired(false)
                .build();

        tokenRepository.save(token);
    }


    private void revokeAllUserTokens(UserEntity user) {
        var validTokensByUser = tokenRepository.findAllValidTokensByUser(user.getId());

        if (validTokensByUser.isEmpty()) {
            return;
        }
        validTokensByUser.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validTokensByUser);
    }

    private void sendValidationEmail(UserEntity user) throws MessagingException {
        var newToken = generateAndSaveActivationCode(user);

        emailService.sendEmailAcctivateAccount(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                newToken,
                ACTIVATION_ACCOUNT
        );
    }

    private String generateAndSaveActivationCode(UserEntity user) {
        String generatedCode = generateActivationCode();

        TokenCodeEntity token = TokenCodeEntity.builder()
                .token(generatedCode)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .user(user)
                .build();
        codeRepository.save(token);

        return generatedCode;
    }

    private String generateActivationCode() {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < 6; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }


    public AccountInfo convertUserToACI(UserEntity user) {
        return AccountInfo.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accountNumber(String.valueOf(user.getPrimaryAccount().getAccountNumber()))
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

}
