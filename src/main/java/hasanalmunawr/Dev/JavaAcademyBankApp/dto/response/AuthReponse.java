package hasanalmunawr.Dev.JavaAcademyBankApp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthReponse {

    @JsonProperty("acces_token")
    private String accessToken;

    @JsonProperty("access_token_expiry")
    private int accessTokenExpiry;

    @JsonProperty("token_type")
    private TokenType tokenType;

    private String username;
}
