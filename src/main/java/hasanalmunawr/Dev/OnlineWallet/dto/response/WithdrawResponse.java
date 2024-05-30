package hasanalmunawr.Dev.OnlineWallet.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class WithdrawResponse {


    @JsonProperty("withdraw_id")
    private String withdrawalId;
    @JsonProperty("withdraw_time")
    private String withdrawTime;
    @JsonProperty("withdraw_date")
    private String withdrawDate;
    private Double amount;
    @JsonProperty("transaction_fee")
    private double transactionFee;
    @JsonProperty("is_succeed")
    private boolean isSuccess;
    @JsonProperty("error_message")
    private String errorMessage;


}
