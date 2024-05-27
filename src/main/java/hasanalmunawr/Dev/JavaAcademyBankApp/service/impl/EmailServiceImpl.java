package hasanalmunawr.Dev.JavaAcademyBankApp.service.impl;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.EmailDetails;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
        log.info("[EmailServiceImpl:sendEmailAlert] Try Sending email message To {}", emailDetails.getRecipient());
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText((String) emailDetails.getMessageBody());
            mailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(mailMessage);
            log.info("[EmailServiceImpl:sendEmailAlert] Succes Sent email message To {}",
                    emailDetails.getRecipient());
            System.out.printf("Main sent succesfully");
        } catch (MailException e) {
            log.error("[EmailServiceImpl:sendEmailAlert] Failed sending email message due to {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
