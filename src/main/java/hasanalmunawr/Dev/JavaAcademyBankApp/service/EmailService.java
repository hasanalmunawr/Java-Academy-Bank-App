package hasanalmunawr.Dev.JavaAcademyBankApp.service;

import hasanalmunawr.Dev.JavaAcademyBankApp.entity.EmailTemplateName;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.TransactionType;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface EmailService {

//    void sendEmailAlert(EmailDetails emailDetails);


    void sendEmailAcctivateAccount(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String activationCode,
            String subject) throws MessagingException;

    void sendEmailTransaction(
            String email,
            TransactionType transactionType,
            String transactionId,
            String recipientName,
            String recipientNumber,
            String nominal
    ) throws IOException;

    void sendEmailDepositTransaction(
            String username,
            String accountNumber,
            String transactionId,
            String nominal,
            String email
    ) throws IOException, MessagingException;
}
