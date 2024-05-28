package hasanalmunawr.Dev.JavaAcademyBankApp.service;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.RecipientRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.DepositResponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.WithdrawRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

public interface TransactionService {

     void deposit(DepositRequest request, UserEntity currentUser) throws MessagingException, IOException;
     void withdraw(WithdrawRequest request, UserEntity currentUser);
     void transfer(RecipientRequest request, UserEntity currentUser);

}
