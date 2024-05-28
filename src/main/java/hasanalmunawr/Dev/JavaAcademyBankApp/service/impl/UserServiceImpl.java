package hasanalmunawr.Dev.JavaAcademyBankApp.service.impl;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.AccountInfo;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.LoginRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.RegisterRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.AuthReponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.RegisterResponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.*;
import hasanalmunawr.Dev.JavaAcademyBankApp.exception.AccountNotFoundException;
import hasanalmunawr.Dev.JavaAcademyBankApp.exception.ActivationTokenException;
import hasanalmunawr.Dev.JavaAcademyBankApp.exception.EntityNotFound;
import hasanalmunawr.Dev.JavaAcademyBankApp.exception.UserAlreadyExistException;
import hasanalmunawr.Dev.JavaAcademyBankApp.mapper.UserMapper;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.TokenCodeRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.TokenRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.UserRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.AccountService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.EmailService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import static hasanalmunawr.Dev.JavaAcademyBankApp.utils.EmailUtils.ACTIVATION_ACCOUNT;
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
            Optional<UserEntity> byEmail = userRepository.findByEmail(request.getEmail());
            if (byEmail.isPresent()) {
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
            log.info("Created user: {}", savedUser.getCreatedAt().toString());
            sendValidationEmail(savedUser);

            return RegisterResponse.builder()
                    .accountInfo(convertUserToACI(user))
                    .build();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @Override
    public AuthReponse authentication(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                        )
                );
            var userLogin = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(AccountNotFoundException::new);

            String refreshToken = jwtService.generateRefreshToken(userLogin);
            long accessExpiration = jwtService.getJwtExpiration();

            return AuthReponse.builder()
                    .username(userLogin.getEmail())
//                    .tokenType(userLogin.getTokens().get(0).getTokenType())
                    .accessTokenExpiry((int) accessExpiration)
                    .accessToken(refreshToken)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void activateAccount(String tokenCode) {
        TokenCodeEntity codeEntity = codeRepository.findByToken(tokenCode)
                .orElseThrow(() -> new EntityNotFound("Token Not Found"));

        if (LocalDateTime.now().isAfter(codeEntity.getExpiresAt())) {
            throw new ActivationTokenException("Activation token has expired. A new token has been send to tha same email " +
                    "address");
        }

        UserEntity userEntity = userRepository.findById(codeEntity.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User " +
                        "Not Found"));
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
    }

    private void sendValidationEmail(UserEntity user) throws MessagingException {
        var newToken = generateAndSaveActivationCode(user);

        emailService.sendEmail(
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
//                .createAt(user.getCreatedAt())
                .build();
    }

}
