package hasanalmunawr.Dev.OnlineWallet.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hasanalmunawr.Dev.OnlineWallet.dto.AccountInfo;
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
