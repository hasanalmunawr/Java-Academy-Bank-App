package hasanalmunawr.Dev.JavaAcademyBankApp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

    @NotBlank(message = "Email Must Not Blank")
    @NotNull(message = "Email Must Not Null")
    @Email(message = "Email Is UnFormatted")
    private String email;

    @NotBlank(message = "Password Must Not Blank")
    @NotNull(message = "Password Must Not Null")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;
}
