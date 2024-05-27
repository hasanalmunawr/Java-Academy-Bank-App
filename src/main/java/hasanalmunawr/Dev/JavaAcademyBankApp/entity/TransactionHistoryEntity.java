package hasanalmunawr.Dev.JavaAcademyBankApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistoryEntity {

    @Id
    @GeneratedValue
    private Integer transactionId;
    private Integer accountId;
    private LocalDateTime transactionDate;
    private String transactionType;
    private String transactionStatus;
    private String recipientAccount;

}
