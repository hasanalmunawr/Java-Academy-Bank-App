package hasanalmunawr.Dev.JavaAcademyBankApp.service.impl;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.EmailDetails;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.RecipientRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.WithdrawRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.EmailTemplateName;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryAccount;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryTransaction;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import hasanalmunawr.Dev.JavaAcademyBankApp.exception.AccountNotFoundException;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.PrimaryAccountRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.UserRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.EmailService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.PrimaryTransactionService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.TransactionService;
import hasanalmunawr.Dev.JavaAcademyBankApp.utils.TransactionUtils;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDate.now;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final PrimaryAccountRepository accountRepository;
    private final EmailService emailService;
    private final PrimaryTransactionService transactionService;


    @Override
    public void deposit(DepositRequest request, UserEntity currentUser) throws MessagingException {
        log.info("[TransactionServiceImpl:deposit] Processing Deposit for Account : {}", currentUser.getFullName());
        UserEntity user = userRepository.findByEmail(currentUser.getUsername()).orElseThrow();
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

        emailService.sendEmail(user.getEmail(), user.getFullName(), EmailTemplateName.ACTIVATE_ACCOUNT,"", "" );
        log.info("[TransactionServiceImpl:deposit]  Deposit Succeed for Account : {}", currentUser.getFullName());

    }

    @Override
    public void withdraw(WithdrawRequest request, UserEntity currentUser) {
        log.info("[AccountServiceImpl:withdraw] Withdrawing Account : {}", currentUser.getEmail());
        try {
            PrimaryAccount primaryAccount = currentUser.getPrimaryAccount();
            double availableBalance = primaryAccount.getAccountBalance();
            if (availableBalance < request.getAmount()) {
                log.error("[AccountServiceImpl");
                throw new Exception("Balance Is Not Enough");
            }

            log.info("[AccountServiceImpl:withdraw] Request Account : {}", request);
            primaryAccount.setAccountBalance(availableBalance - request.getAmount());
            PrimaryAccount saveAccount = accountRepository.save(primaryAccount);
            userRepository.save(currentUser);
            log.info("[AccountServiceImpl:withdraw] Account Successfully Withdrawn : {}", currentUser.getEmail());


            PrimaryTransaction primaryTransaction = PrimaryTransaction.builder()
                    .dateTime(LocalDateTime.now())
                    .description("Deposit To Primary Account")
                    .type("DEPOSIT")
                    .status("Succeed")
                    .amount(String.valueOf(request.getAmount()))
                    .availableBalance(saveAccount.getAccountBalance())
                    .build();

            PrimaryTransaction savingTransaction = transactionService.savingTransaction(primaryTransaction);


            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(currentUser.getEmail())
                    .subject("Transaction Jurney")
                    .messageBody(TransactionUtils
                            .withdrawTransaction(currentUser, savingTransaction))
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void transfer(RecipientRequest request, UserEntity currentUser) {
        try {
            PrimaryAccount recipientAccount = accountRepository.findByAccountNumber(request.getAccountNumber())
                    .orElseThrow(AccountNotFoundException::new);
            PrimaryAccount currentAccount = currentUser.getPrimaryAccount();

//        THE BALANCE CAN NOT TO BE 0, AT LEST 0.1
            if (currentAccount.getAccountBalance() <= recipientAccount.getAccountBalance()) {
                throw new BadRequestException("Balance Is Not Enough To Transfer");
            }
            transferProcessing(recipientAccount, currentAccount, request.getAmount());

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void transferProcessing(PrimaryAccount recipientAccount, PrimaryAccount currentAccount, double amount) {
//        Reduce the current user account balance
        double remainBalance = currentAccount.getAccountBalance() - amount;

        recipientAccount.setAccountBalance(recipientAccount.getAccountBalance() + amount);
        accountRepository.saveAll(List.of(recipientAccount, currentAccount));

    }


//    private boolean checkAccountNumber(String number) {
//        userRepository.
//    }
}
