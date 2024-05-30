package hasanalmunawr.Dev.OnlineWallet.repository;

import hasanalmunawr.Dev.OnlineWallet.entity.TransactionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryEntity, Integer> {

}
