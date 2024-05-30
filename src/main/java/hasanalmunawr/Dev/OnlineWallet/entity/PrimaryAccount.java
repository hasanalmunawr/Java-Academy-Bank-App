package hasanalmunawr.Dev.OnlineWallet.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrimaryAccount {

    @Id
    @GeneratedValue()
    private Integer id;
    @Column(name = "account_number", unique = true, nullable = false)
    private Integer accountNumber;
    private double accountBalance;

    @OneToOne(mappedBy = "primaryAccount") // Owning side is PrimaryAccount
    private UserEntity user;

    @OneToMany(mappedBy = "primaryAccount", cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrimaryTransaction> primaryTransactions;
}
