package hasanalmunawr.Dev.JavaAcademyBankApp.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tokens")
public class Token extends Auditable{


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public Integer id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean isRevoked;
    public boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserEntity user;
}
