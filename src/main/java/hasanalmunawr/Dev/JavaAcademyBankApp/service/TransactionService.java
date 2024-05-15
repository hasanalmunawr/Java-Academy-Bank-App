package hasanalmunawr.Dev.JavaAcademyBankApp.service;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.RecipientRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.DepositResponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.WithdrawRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface TransactionService {

     void deposit(DepositRequest request, UserEntity currentUser);
     void withdraw(WithdrawRequest request, UserEntity currentUser);
     void transfer(RecipientRequest request, UserEntity currentUser);

}
