package hasanalmunawr.Dev.JavaAcademyBankApp.service;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.DepositResponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.WithdrawRequest;

public interface TransactionService {

     DepositResponse depositFunds(DepositRequest request);

     DepositResponse withdrawFunds(WithdrawRequest request);

}
