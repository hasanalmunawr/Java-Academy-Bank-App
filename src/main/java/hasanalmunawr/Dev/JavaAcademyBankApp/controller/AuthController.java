package hasanalmunawr.Dev.JavaAcademyBankApp.controller;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.LoginRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.RegisterRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping(path = "/register",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(
            @RequestBody @Valid RegisterRequest request) {
      log.info("[auth] Sign up request received: {}", request.getEmail());
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping(path = "/login",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signIn(
            @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(userService.authentication(request));
    }


    @GetMapping(path = "/activate-account")
    public void confirm(
            @RequestParam String tokenCode
    ) throws MessagingException {
        userService.activateAccount(tokenCode);
    }


    @GetMapping(path = "/test")
    public String hello(
            @RequestParam String tokenCode
    ) throws MessagingException {
        return "Hello from auth controller";
    }



//    @DeleteMapping(path = "/logout")
//    public void logout(
//            @AuthenticationPrincipal UserEntity currentUser
//    ) {
//        userService.logout(currentUser);
//    }
}

