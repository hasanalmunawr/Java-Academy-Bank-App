package hasanalmunawr.Dev.JavaAcademyBankApp.controller;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.LoginRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.UserRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> signUp(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }


    @GetMapping(path = "/activate-account")
    public void confirm(
            @RequestParam String tokenCode
    ) throws MessagingException {
        userService.activateAccount(tokenCode);
    }
}

