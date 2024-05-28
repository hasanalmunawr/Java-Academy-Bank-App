package hasanalmunawr.Dev.JavaAcademyBankApp.service;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.TransferRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.WithdrawRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.TransferResponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.WithdrawResponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import jakarta.mail.MessagingException;
import org.apache.coyote.BadRequestException;

import java.io.IOException;

public interface TransactionService {

     void deposit(DepositRequest request, UserEntity currentUser) throws MessagingException, IOException;
     WithdrawResponse withdraw(WithdrawRequest request, UserEntity currentUser);
     TransferResponse transfer(TransferRequest request, UserEntity currentUser) throws BadRequestException;

}
