package hasanalmunawr.Dev.JavaAcademyBankApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrimaryAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "account_number", unique = true, nullable = false)
    private Integer accountNumber;
    private double accountBalance;

    @OneToOne(mappedBy = "primaryAccount") // Owning side is PrimaryAccount
    private UserEntity user;

    @OneToMany(mappedBy = "primaryAccount", cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrimaryTransaction> primaryTransactions;
}
