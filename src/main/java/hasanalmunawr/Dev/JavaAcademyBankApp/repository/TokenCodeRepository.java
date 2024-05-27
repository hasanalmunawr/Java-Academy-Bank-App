package hasanalmunawr.Dev.JavaAcademyBankApp.repository;

import hasanalmunawr.Dev.JavaAcademyBankApp.entity.TokenCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenCodeRepository extends JpaRepository<TokenCodeEntity, Integer> {

    Optional<TokenCodeEntity> findByToken(String token);
}
