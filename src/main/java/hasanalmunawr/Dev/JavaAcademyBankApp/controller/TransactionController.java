package hasanalmunawr.Dev.JavaAcademyBankApp.controller;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.RecipientRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.WithdrawRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.AccountService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private final AccountService accountService;

    @PostMapping(path = "/deposit")
    public ResponseEntity<?> deposit(
            @RequestBody DepositRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        transactionService.deposit(request, userDetails);
        return ResponseEntity.ok("This is a deposit for " + userDetails.getUsername());
//        return ResponseEntity.ok(transactionService.depositFunds(request));
    }


    @GetMapping(path = "/withdraw")
    public ResponseEntity<?> withdraw(
            @RequestBody WithdrawRequest request,
            @AuthenticationPrincipal UserEntity user) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Object details = auth.getDetails();
//        Principal principal = (Principal) auth.getPrincipal();
//        String password = (String) auth.getCredentials();
//        principal.
        transactionService.withdraw(request, user);
        return ResponseEntity.ok("This is a withdraw from : " + user.getFullName());
    }


    @PostMapping(path = "/transfer")
    public ResponseEntity<?> tranfers(
            @RequestBody RecipientRequest request,
            @AuthenticationPrincipal UserEntity user) {
        transactionService.transfer(request, user);
        return ResponseEntity.ok(user);
    }
}
