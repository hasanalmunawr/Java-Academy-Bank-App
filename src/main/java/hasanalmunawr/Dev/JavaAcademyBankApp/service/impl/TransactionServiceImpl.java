package hasanalmunawr.Dev.JavaAcademyBankApp.service.impl;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.WithdrawRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.DepositResponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.EmailDetails;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryAccount;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import hasanalmunawr.Dev.JavaAcademyBankApp.exception.AccountNotFoundException;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.PrimaryAccountRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.UserRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.EmailService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.TransactionService;
import hasanalmunawr.Dev.JavaAcademyBankApp.utils.TransactionUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

import static java.time.LocalDate.now;

@Service
@Slf4j
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrimaryAccountRepository accountRepository;

    @Autowired
    private EmailService emailService;


    @Override
    public DepositResponse depositFunds(DepositRequest request) {
        log.info("[TransactionServiceImpl:depositAccount] Proseccing deposit for account :{}", request.getRecipientNumber());

//        INI MASIH NULL
        PrimaryAccount account = accountRepository.findByAccountNumber(request.getRecipientNumber()).orElseThrow();

        UserEntity userEntity =
                userRepository.findByPrimaryAccountId(account.getId())
                        .orElseThrow(() ->
                                new AccountNotFoundException());

        account.setAccountBalance(account.getAccountBalance() + request.getNominal());
        userRepository.save(userEntity);
        log.info("[TransactionServiceImpl:depositAccount] Successful account deposit process for  :{}",
                userEntity.getUsername());

//        EmailDetails emailDetails = EmailDetails.builder()
//                .recipient(userEntity.getEmail())
//                .subject("Transaction Jurney")
//                .messageBody(TransactionUtils.depositTransaction(request, userEntity.getUsername()))
//                .build();
//        emailService.sendEmailAlert(emailDetails);

       return DepositResponse.builder()
                .recipientNumber(String.valueOf(userEntity.getPrimaryAccount().getAccountNumber()))
                .recipientName(userEntity.getUsername())
                .nominal(request.getNominal())
                .date(now())
                .time(LocalTime.now())
                .build();
    }

    @Override
    public DepositResponse withdrawFunds(WithdrawRequest request) {
        return null;
    }


//    private boolean checkAccountNumber(String number) {
//        userRepository.
//    }
}
