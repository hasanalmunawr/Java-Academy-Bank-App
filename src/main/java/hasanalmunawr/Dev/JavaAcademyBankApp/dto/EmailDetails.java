package hasanalmunawr.Dev.JavaAcademyBankApp.dto;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetails <T>{

    private String recipient;
    private T messageBody;
    private String subject;
    private String attachment;
}
