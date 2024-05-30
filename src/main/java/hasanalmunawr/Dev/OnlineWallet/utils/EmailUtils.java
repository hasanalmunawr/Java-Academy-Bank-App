package hasanalmunawr.Dev.OnlineWallet.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;

public class EmailUtils {

    public static String ACTIVATION_ACCOUNT = "Account activation";
    public static String RESET_PASSWORD = "Reset Password";
    public static String TRANSACTION_JOURNEY = "Transaction Journey";


    public static String emailMessageTransactions(
            String typeTransaction,
            String transactionId,
            String recipientName,
            String recipientNumber,
            String nominal) throws IOException {

        Path pathToHtmlFile = Path.of("src/main/resources/templates/withdraw_transaction.html");
        String htmlContent = Files.readString(pathToHtmlFile);

        htmlContent.replace("{type-transaction}", typeTransaction);
        htmlContent.replace("${transaction-id}", transactionId);
        htmlContent.replace("${transaction-date}", LocalDate.now().toString());
        htmlContent.replace("${transaction-time}", LocalTime.now().toString());
        htmlContent.replace("${recipient-name}", recipientName);
        htmlContent.replace("${recipient-number}", recipientNumber);
        htmlContent.replace("${nominal}", nominal);

        return htmlContent;
    }

    public static String emailMessageDeposit(
            String username,
            String accountNumber,
            String transactionId,
            String nominal
    ) throws IOException {
        Path pathToHtmlFile = Path.of("src/main/resources/templates/deposit_transaction.html");
        String htmlContent = Files.readString(pathToHtmlFile);

        htmlContent = htmlContent.replace("{username}", username);
        htmlContent = htmlContent.replace("{account-number}", accountNumber);
        htmlContent = htmlContent.replace("{transaction-id}", transactionId);
        htmlContent = htmlContent.replace("{nominal}", nominal);
        htmlContent = htmlContent.replace("{date}", LocalDate.now().toString());
        htmlContent = htmlContent.replace("{time}", LocalTime.now().toString());

        return htmlContent;
    }
}
