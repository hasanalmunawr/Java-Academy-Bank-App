package hasanalmunawr.Dev.JavaAcademyBankApp.repository;

import hasanalmunawr.Dev.JavaAcademyBankApp.entity.TransactionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryEntity, Integer> {

}
