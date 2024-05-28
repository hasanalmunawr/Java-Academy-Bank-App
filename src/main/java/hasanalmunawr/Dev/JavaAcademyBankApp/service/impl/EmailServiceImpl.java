package hasanalmunawr.Dev.JavaAcademyBankApp.service.impl;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.EmailDetails;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.EmailTemplateName;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.TransactionType;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.EmailService;
import hasanalmunawr.Dev.JavaAcademyBankApp.utils.EmailUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static hasanalmunawr.Dev.JavaAcademyBankApp.utils.EmailUtils.TRANSACTION_JOURNEY;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String senderEmail;

    // OPTIONAL FOR MORE FEATURE WITH FRONT END
    private String confirmationUrl;



    @Async
    public void sendEmailAcctivateAccount(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String activationCode,
            String subject
    ) throws MessagingException {
        String templateName;
        if (emailTemplate == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate.getName();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom(senderEmail);
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);
        mailSender.send(mimeMessage);
    }

    @Async
    public void sendEmailDepositTransaction(
            String username,
            String accountNumber,
            String transactionId,
            String nominal,
            String email
    ) throws IOException, MessagingException {
        String messageHtml = EmailUtils.emailMessageDeposit(
                username,
                accountNumber,
                transactionId,
                nominal);

        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject(TRANSACTION_JOURNEY);
        message.setContent(messageHtml, "text/html; charset=utf-8");
        mailSender.send(message);
    }


    @Async
    public void sendEmailTransaction(
            String email,
            TransactionType transactionType,
            String transactionId,
            String recipientName,
            String recipientNumber,
            String nominal
    ) throws IOException {
        try {
            String messageHtml = EmailUtils.emailMessageTransactions(
                    transactionType.getName(),
                    transactionId,
                    recipientName,
                    recipientNumber,
                    nominal
            );
            MimeMessage message = mailSender.createMimeMessage();

            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject(TRANSACTION_JOURNEY);
            message.setContent(messageHtml, "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }





}
