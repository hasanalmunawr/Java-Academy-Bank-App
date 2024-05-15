package hasanalmunawr.Dev.JavaAcademyBankApp.utils;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryTransaction;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionUtils {

    public static final String SUCCES_DEPOSIT = "Succes Deposit Account";

    public static String depositTransaction(DepositRequest depositRequest, String name, String idTransaction) {
        return """
                No Transaction : %s
                Transaction Date: %s
                Transaction Time : %s
                Recipient Name : %s
                Recipient Number : %s
                Nominal :  %s
             
                """.formatted(idTransaction, LocalDate.now(), LocalTime.now(),name, depositRequest.getRecipientNumber(),
                depositRequest.getNominal());
    }

    public static String withdrawTransaction(UserEntity user, PrimaryTransaction transaction) {
        return """
                No Transaction : %s
                Transaction Date: %s
                Transaction Time : %s
                Withdraw Nominal :  %s
             
                """.formatted(transaction.getId(), LocalDate.now(), LocalTime.now(),
                transaction.getAmount());
    }



}
