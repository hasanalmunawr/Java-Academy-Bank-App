package hasanalmunawr.Dev.JavaAcademyBankApp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositRequest {

    private Integer recipientNumber;
    private double nominal;
    private String description;
}
