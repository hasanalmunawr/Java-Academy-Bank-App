package hasanalmunawr.Dev.JavaAcademyBankApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfo {

    private String firstName;
    private String lastName;
    private String accountNumber;
    private String email;
    private String phone;
    private String createAt;
}
