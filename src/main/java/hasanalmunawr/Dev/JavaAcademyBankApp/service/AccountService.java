package hasanalmunawr.Dev.JavaAcademyBankApp.service;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.WithdrawRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryAccount;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

public interface AccountService {

    PrimaryAccount createPrimaryAccount();

    void deposit2(String accountType, double amount, Principal principal);
    void deposit(DepositRequest request, UserDetails userDetails);

    void withdraw(WithdrawRequest request, UserEntity userEntity);
}
