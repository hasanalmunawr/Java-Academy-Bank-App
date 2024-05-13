package hasanalmunawr.Dev.JavaAcademyBankApp.utils;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionUtils {

    public static final String SUCCES_DEPOSIT = "Succes Deposit Account";

    public static String depositTransaction(DepositRequest depositRequest, String name) {
        return """
                No Transaction : 
                Transaction Date: %s
                Transaction Time : %s
                Recipient Name : %s
                Recipient Number : %s
                Nominal :  %s
             
                """.formatted(LocalDate.now(), LocalTime.now(),name, depositRequest.getRecipientNumber(),
                depositRequest.getNominal());
    }
}
