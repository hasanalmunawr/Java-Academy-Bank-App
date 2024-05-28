package hasanalmunawr.Dev.JavaAcademyBankApp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.AccountInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {

    @JsonProperty("account_info")
    private AccountInfo accountInfo;
}
