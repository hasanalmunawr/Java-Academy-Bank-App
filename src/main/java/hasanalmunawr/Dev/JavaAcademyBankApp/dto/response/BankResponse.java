package hasanalmunawr.Dev.JavaAcademyBankApp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.AccountInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class BankResponse {
    private String message;
    private AccountInfo accountInfo;
    private DepositResponse response;

    @JsonProperty("access_token")
    private String accessToken;
}
