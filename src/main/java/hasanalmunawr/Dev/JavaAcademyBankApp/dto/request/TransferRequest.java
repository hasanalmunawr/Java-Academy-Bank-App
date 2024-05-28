package hasanalmunawr.Dev.JavaAcademyBankApp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TransferRequest {

    @NotNull(message = "Number Recipient Can Not Be Null")
    private Integer accountNumber;
    private double amount;
    private String recipientEmail;
    private String message;
    @NotNull(message = "Password Confirmation Can Not Be Null")
    private String passwordConfirmation;
}
