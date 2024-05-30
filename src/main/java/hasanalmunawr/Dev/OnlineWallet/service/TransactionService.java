package hasanalmunawr.Dev.OnlineWallet.service;

import hasanalmunawr.Dev.OnlineWallet.dto.request.DepositRequest;
import hasanalmunawr.Dev.OnlineWallet.dto.request.TransferRequest;
import hasanalmunawr.Dev.OnlineWallet.dto.request.WithdrawRequest;
import hasanalmunawr.Dev.OnlineWallet.dto.response.TransferResponse;
import hasanalmunawr.Dev.OnlineWallet.dto.response.WithdrawResponse;
import hasanalmunawr.Dev.OnlineWallet.entity.UserEntity;
import jakarta.mail.MessagingException;
import org.apache.coyote.BadRequestException;

import java.io.IOException;

public interface TransactionService {

     void deposit(DepositRequest request, UserEntity currentUser) throws MessagingException, IOException;
     WithdrawResponse withdraw(WithdrawRequest request, UserEntity currentUser);
     TransferResponse transfer(TransferRequest request, UserEntity currentUser) throws BadRequestException;

}
