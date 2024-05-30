package hasanalmunawr.Dev.OnlineWallet.controller;

import hasanalmunawr.Dev.OnlineWallet.dto.request.ChangeRequest;
import hasanalmunawr.Dev.OnlineWallet.entity.UserEntity;
import hasanalmunawr.Dev.OnlineWallet.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(path = "/api/v1/home")
@RequiredArgsConstructor
@Tag(name = "Home")
@Slf4j
public class HomeController {

    private final UserService userService;

    public ResponseEntity<?> changePassword(
            @RequestBody ChangeRequest request,
            Authentication currentUser) {
        UserEntity principal = (UserEntity) currentUser.getPrincipal();

        if (!Objects.equals(request.newPassword(), request.confirmPassword())) {
            throw new RequestRejectedException("Passwords do not match");
        }
        userService.changePassword(principal, request);
        return ResponseEntity.accepted().body("Succeed Change The Password");
    }
}
