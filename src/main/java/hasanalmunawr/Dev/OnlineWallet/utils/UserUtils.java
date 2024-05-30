package hasanalmunawr.Dev.OnlineWallet.utils;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.Month;
import java.time.Year;

public class UserUtils {

    public static final String ACCOUNT_CREATED = "Account Succes Created";

    public static Integer generateAccountNumber() {
        // Get the current year
        Year year = Year.now();
        int yearInt = year.getValue();  // Get year as integer

        // Generate a random 6-digit number (assuming remaining digits are for account differentiation)
        int randomPart = (int) (Math.random() * 1000000); // Generates a random number between 0 and 999999

        // Combine the year and random part into an integer, ensuring 8 digits
        int accountNumber = (yearInt * 1000000) + randomPart;

        return accountNumber;
    }




}
