package hasanalmunawr.Dev.JavaAcademyBankApp.service.impl;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.EmailDetails;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.WithdrawRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryAccount;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryTransaction;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.PrimaryAccountRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.UserRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.AccountService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.EmailService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.PrimaryTransactionService;
import hasanalmunawr.Dev.JavaAcademyBankApp.utils.TransactionUtils;
import hasanalmunawr.Dev.JavaAcademyBankApp.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {

    private final PrimaryAccountRepository accountRepository;

    private final UserRepository userRepository;

    private final EmailService emailService;

//    private final TransactionService transactionService11;

    private final PrimaryTransactionService transactionService;

    @Override
    public PrimaryAccount createPrimaryAccount() {
        PrimaryAccount account = PrimaryAccount.builder()
                .accountNumber(UserUtils.generateAccountNumber())
                .accountBalance(0L)
                .build();
        log.info("[AccountServiceImpl:createPrimaryAccount] Primary Account Has Created");
        return accountRepository.save(account);
    }

    @Override
    public void deposit2(String accountType, double amount, Principal principal) {

    }

    @Override
    public void deposit(DepositRequest request, UserDetails userDetails) {
        log.info("[AccountServiceImpl:deposit] Processing Deposit for Account : {}", userDetails.getUsername());
        UserEntity user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
//        if (accountType.equalsIgnoreCase("Primary")) {
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        primaryAccount.setAccountBalance(primaryAccount.getAccountBalance() + request.getNominal());
        PrimaryAccount saveAccount = accountRepository.save(primaryAccount);

        LocalDateTime dateTime = LocalDateTime.now();
        PrimaryTransaction primaryTransaction = PrimaryTransaction.builder()
                .dateTime(dateTime)
                .description("Deposit To Primary Account")
                .type("DEPOSIT")
                .status("Succeed")
                .amount(String.valueOf(request.getNominal()))
                .availableBalance(saveAccount.getAccountBalance())
                .build();
        PrimaryTransaction savingTransaction = transactionService.savingTransaction(primaryTransaction);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Transaction Jurney")
                .messageBody(TransactionUtils
                        .depositTransaction(request, user.getFullName(), savingTransaction.getId()))
                .build();
        emailService.sendEmailAlert(emailDetails);
        log.info("[AccountServiceImpl:deposit]  Deposit Succeed for Account : {}", userDetails.getUsername());

//        }
    }

    @Override
    public void withdraw(WithdrawRequest request, UserDetails userDetails) {
        try {
            UserEntity user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            double availableBalance = primaryAccount.getAccountBalance();
            if (availableBalance < request.getAmount()) {
                throw new Exception("Balance Is Not Enough");
            }
            primaryAccount.setAccountBalance(availableBalance - request.getAmount());
            accountRepository.save(primaryAccount);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}
