package hasanalmunawr.Dev.JavaAcademyBankApp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferResponse {

    @JsonProperty("transfer_id")
    private String transferId;
    @JsonProperty("transfer_time")
    private String transferTime;
    @JsonProperty("transfer_date")
    private String transferDate;
    @JsonProperty("is_succeed")
    private boolean isSuccess;
    @JsonProperty("recipient_number")
    private String recipientNumber;
    @JsonProperty("recipient_name")
    private String recipientName;
    private Double amount;
    private String currency;
}