package hasanalmunawr.Dev.JavaAcademyBankApp.service.impl;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.*;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.LoginRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.UserRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.AuthReponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.BankResponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.Token;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.TokenType;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import hasanalmunawr.Dev.JavaAcademyBankApp.exception.AccountNotFoundException;
import hasanalmunawr.Dev.JavaAcademyBankApp.exception.UserAlreadyExistException;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.TokenRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.UserRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.AccountService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.EmailService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.util.Optional;

import static hasanalmunawr.Dev.JavaAcademyBankApp.utils.UserUtils.ACCOUNT_CREATED;
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

    @Autowired
    private  EmailService emailService;
    @Override
    public BankResponse createUser(UserRequest request) {
        log.info("[UserServiceImpl:createUser] Creating User {}", request.getEmail());
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
                    .enabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .build();

            UserEntity savedUser = userRepository.save(user);
            String jwtToken = jwtService.generateToken(savedUser);
            String refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);
            log.info("[UserServiceImpl:createUser] Succes Generated Token User ");


            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(savedUser.getEmail())
                    .subject("ACCOUNT CREATION")
                    .messageBody(MESSAGEBODY(savedUser))
                    .build();
//            emailService.sendEmailAlert(emailDetails);
            log.info("[UserServiceImpl:createUser] Created User {}", request.getEmail());


            return BankResponse.builder()
                    .message(ACCOUNT_CREATED)
                    .accountInfo(convertUserToACI(savedUser))
                    .accessToken(jwtToken)
                    .build();
        } catch (Exception e) {
            log.error("[UserServiceImpl:createUser] Get An error {}", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public AuthReponse login(LoginRequest request) {
        log.info("[UserServiceImpl:login] Try to Macthing Ther user {}", request.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                        )
                );
            var userLogin = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(AccountNotFoundException::new);

//            log.info("The password real :{}", request.getPassword());
//            String hashedLoginPassword = passwordEncoder.encode(request.getPassword());
//            log.info("The password request : {}", hashedLoginPassword);
//            log.info("The password userENti : {}", userLogin.getPassword());

//            if (!passwordEncoder.matches(userLogin.getPassword(), request.getPassword())) {
//                log.error("[UserServiceImpl:login] Password Does Not Match");
//                throw new AuthenticationException("Invalid credentials");  // More specific exception
//            }

            String refreshToken = jwtService.generateRefreshToken(userLogin);
            long accessExpiration = jwtService.getJwtExpiration();


            return AuthReponse.builder()
                    .username(userLogin.getEmail())
                    .tokenType(userLogin.getTokens().get(0).getTokenType())
                    .accessTokenExpiry((int) accessExpiration)
                    .accessToken(refreshToken)
                    .build();
        } catch (Exception e) {
            log.error("[UserServiceImpl:login] Email Or Password Are Not Matcher, {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    private AccountInfo convertUserToACI(UserEntity user) {
        return AccountInfo.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accountNumber(String.valueOf(user.getPrimaryAccount().getAccountNumber()))
                .email(user.getEmail())
                .phone(user.getPhone())
                .createAt(user.getCreatedAt().toString())
                .build();
    }



    private void saveUserToken(UserEntity user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }
    private String MESSAGEBODY(UserEntity user) {
        return """
          Congratulations! Your Account Has Been Created.
          Your Account Details:
          First Name: %s
          Last Name: %s
          Account Number: %s
          Email: %s
          Phone: %s
          Created At: %s
          """.formatted(user.getFirstName(), user.getLastName(), user.getPrimaryAccount().getAccountNumber(),
                user.getEmail(), user.getPhone(), user.getCreatedAt());
    }

}
