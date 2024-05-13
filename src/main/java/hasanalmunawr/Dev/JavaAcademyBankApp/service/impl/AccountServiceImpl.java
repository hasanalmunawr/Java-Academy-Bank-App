package hasanalmunawr.Dev.JavaAcademyBankApp.service.impl;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryAccount;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryTransaction;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.PrimaryAccountRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.UserRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.AccountService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.PrimaryTransactionService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.TransactionService;
import hasanalmunawr.Dev.JavaAcademyBankApp.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.Data;
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
        transactionService.savingTransaction(primaryTransaction);
//        }
    }


    @Override
    public void withdraw(String accountType, double amount, Principal principal) {

    }
}
