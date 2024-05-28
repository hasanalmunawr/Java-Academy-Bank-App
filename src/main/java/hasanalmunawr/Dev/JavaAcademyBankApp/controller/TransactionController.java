package hasanalmunawr.Dev.JavaAcademyBankApp.controller;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.TransferRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.WithdrawRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryTransaction;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.AccountService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.PrimaryTransactionService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.TransactionService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private final AccountService accountService;
    private final PrimaryTransactionService primaryTransactionService;

    @PostMapping(path = "/deposit")
    public ResponseEntity<?> deposit(
            @RequestBody DepositRequest request,
            @AuthenticationPrincipal UserEntity currentUser) throws MessagingException, IOException {

        transactionService.deposit(request, currentUser);
        return ResponseEntity.ok("This is a deposit for " + currentUser.getFullName());
    }


    @GetMapping(path = "/withdraw")
    public ResponseEntity<?> withdraw(
            @RequestBody WithdrawRequest request,
            Authentication currentUser) {
        UserEntity user = (UserEntity) currentUser.getPrincipal();

        transactionService.withdraw(request, user);
        return ResponseEntity.ok("Successfully withdrawn " + request.getAmount());
    }


    @PostMapping(path = "/transfers")
    public ResponseEntity<?> tranfers(
            @RequestBody TransferRequest request,
            Authentication currentUser) {
        UserEntity user = (UserEntity) currentUser.getPrincipal();

        transactionService.transfer(request, user);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<List<PrimaryTransaction>> getHistoryTransaction(
            Authentication currentUser
    ) {
        return ResponseEntity.ok(primaryTransactionService.getHistoryTransactions(currentUser));
    }
}
