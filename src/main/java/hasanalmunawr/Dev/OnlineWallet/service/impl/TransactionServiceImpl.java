package hasanalmunawr.Dev.OnlineWallet.service.impl;

import hasanalmunawr.Dev.OnlineWallet.dto.request.DepositRequest;
import hasanalmunawr.Dev.OnlineWallet.dto.request.TransferRequest;
import hasanalmunawr.Dev.OnlineWallet.dto.request.WithdrawRequest;
import hasanalmunawr.Dev.OnlineWallet.dto.response.TransferResponse;
import hasanalmunawr.Dev.OnlineWallet.dto.response.WithdrawResponse;
import hasanalmunawr.Dev.OnlineWallet.exception.AccountNotFoundException;
import hasanalmunawr.Dev.OnlineWallet.exception.OperationNotPermittedException;
import hasanalmunawr.Dev.OnlineWallet.repository.PrimaryAccountRepository;
import hasanalmunawr.Dev.OnlineWallet.repository.UserRepository;
import hasanalmunawr.Dev.OnlineWallet.service.EmailService;
import hasanalmunawr.Dev.OnlineWallet.service.PrimaryTransactionService;
import hasanalmunawr.Dev.OnlineWallet.service.TransactionService;
import hasanalmunawr.Dev.OnlineWallet.entity.PrimaryAccount;
import hasanalmunawr.Dev.OnlineWallet.entity.PrimaryTransaction;
import hasanalmunawr.Dev.OnlineWallet.entity.TransactionType;
import hasanalmunawr.Dev.OnlineWallet.entity.UserEntity;
import hasanalmunawr.Dev.OnlineWallet.utils.TransactionUtils;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private final PasswordEncoder passwordEncoder;
    private final PrimaryTransactionService transactionService;


    @Override
    public void deposit(DepositRequest request, UserEntity currentUser) throws MessagingException, IOException {
        log.info("[TransactionServiceImpl:deposit] Processing Deposit for Account : {}", currentUser.getEmail());

        PrimaryAccount primaryAccount = currentUser.getPrimaryAccount();
        primaryAccount.setAccountBalance(primaryAccount.getAccountBalance() + request.getNominal());
        PrimaryAccount saveAccount = accountRepository.save(primaryAccount);

        LocalDateTime dateTime = LocalDateTime.now();
        PrimaryTransaction primaryTransaction = PrimaryTransaction.builder()
                .dateTime(dateTime)
                .description(TransactionType.DEPOSIT_DESCRIPTION.getName())
                .type(TransactionType.DEPOSIT.getName())
                .status(TransactionUtils.SUCCEED)
                .amount(String.valueOf(request.getNominal()))
                .availableBalance(saveAccount.getAccountBalance())
                .build();
        PrimaryTransaction savingTransaction = transactionService.savingTransaction(primaryTransaction);

        emailService.sendEmailDepositTransaction(
                currentUser.getFullName(),
                primaryAccount.getAccountNumber().toString(),
                savingTransaction.getId(),
                savingTransaction.getAmount(),
                currentUser.getEmail());
        log.info("[TransactionServiceImpl:deposit]  Deposit Succeed for Account : {}", currentUser.getFullName());

    }

    @Transactional
    @Override
    public WithdrawResponse withdraw(WithdrawRequest request, UserEntity currentUser) {
        log.info("[AccountServiceImpl:withdraw] Withdrawing Account : {}", currentUser.getEmail());

        final double fee = 2000;
        if (checkPassword(request.getPassword(), currentUser.getPassword())) {
            PrimaryAccount primaryAccount = currentUser.getPrimaryAccount();
            double availableBalance = primaryAccount.getAccountBalance();
            if (availableBalance < request.getAmount() + fee) {
                throw new OperationNotPermittedException("Balance Is Not Enough");
            }

            primaryAccount.setAccountBalance(availableBalance - request.getAmount() + fee);
            PrimaryAccount saveAccount = accountRepository.save(primaryAccount);
            userRepository.save(currentUser);

            PrimaryTransaction primaryTransaction = PrimaryTransaction.builder()
                    .dateTime(LocalDateTime.now())
                    .description(TransactionType.WITHDRAWAL_DESCRIPTION.getName())
                    .type(TransactionType.WITHDRAWAL.getName())
                    .status(TransactionUtils.SUCCEED)
                    .amount(String.valueOf(request.getAmount()))
                    .availableBalance(saveAccount.getAccountBalance())
                    .build();

            PrimaryTransaction savingTransaction = transactionService.savingTransaction(primaryTransaction);

            return WithdrawResponse.builder()
                    .withdrawalId((savingTransaction.getId()))
                    .withdrawTime(LocalTime.now().toString())
                    .withdrawDate(LocalDateTime.now().toString())
                    .amount(request.getAmount())
                    .transactionFee(fee)
                    .isSuccess(true)
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Password");
        }

    }
    @Override
    public TransferResponse transfer(TransferRequest request, UserEntity currentUser) throws BadRequestException {
        if (checkPassword(currentUser.getPassword(), request.getPasswordConfirmation())) {

            PrimaryAccount recipientAccount = accountRepository.findByAccountNumber(request.getAccountNumber())
                    .orElseThrow(AccountNotFoundException::new);
            var currentAccount = currentUser.getPrimaryAccount();

//        THE BALANCE CAN NOT TO BE 0, AT LEST 0.1
            if (currentAccount.getAccountBalance() <= recipientAccount.getAccountBalance()) {
                throw new BadRequestException("Balance Is Not Enough To Transfer");
            }
            transferProcessing(recipientAccount, currentAccount, request.getAmount());

            var primaryTransaction = PrimaryTransaction.builder()
                    .dateTime(LocalDateTime.now())
                    .description(TransactionType.TRANSFER_DESCRIPTION.getName())
                    .type(TransactionType.TRANSFER.getName())
                    .status(TransactionUtils.SUCCEED)
                    .amount(String.valueOf(request.getAmount()))
                    .availableBalance(currentAccount.getAccountBalance())
                    .build();

            var savingTransaction = transactionService.savingTransaction(primaryTransaction);

            return TransferResponse.builder()
                    .transferId(savingTransaction.getId())
                    .transferTime(String.valueOf(now()))
                    .transferDate(String.valueOf(now()))
                    .recipientName(recipientAccount.getUser().getFullName())
                    .recipientNumber(String.valueOf(recipientAccount.getAccountNumber()))
                    .amount(request.getAmount())
                    .build();
        } else {
            throw new BadRequestException("Invalid Password");
        }

    }

    private void transferProcessing(PrimaryAccount recipientAccount, PrimaryAccount currentAccount, double amount) {
//        Reduce the current user account balance
        double remainBalance = currentAccount.getAccountBalance() - amount;

        recipientAccount.setAccountBalance(recipientAccount.getAccountBalance() + amount);
        accountRepository.saveAll(List.of(recipientAccount, currentAccount));

    }




    private boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

}
