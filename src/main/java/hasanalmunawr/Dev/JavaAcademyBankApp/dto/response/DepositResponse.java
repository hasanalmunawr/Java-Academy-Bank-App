package hasanalmunawr.Dev.JavaAcademyBankApp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DepositResponse {

    @JsonProperty("recipinet_number")
    private String recipientNumber;
    @JsonProperty("recipient_name")
    private String recipientName;
    private double nominal;
    private LocalDate date;
    private LocalTime time;
}
