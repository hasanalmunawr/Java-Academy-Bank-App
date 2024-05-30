package hasanalmunawr.Dev.OnlineWallet.repository;

import hasanalmunawr.Dev.OnlineWallet.entity.TokenCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenCodeRepository extends JpaRepository<TokenCodeEntity, Integer> {

    Optional<TokenCodeEntity> findByToken(String token);
}
