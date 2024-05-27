package hasanalmunawr.Dev.JavaAcademyBankApp.service;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.EmailDetails;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.EmailTemplateName;
import jakarta.mail.MessagingException;

public interface EmailService {

//    void sendEmailAlert(EmailDetails emailDetails);


    void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String activationCode,
            String subject) throws MessagingException;
}
