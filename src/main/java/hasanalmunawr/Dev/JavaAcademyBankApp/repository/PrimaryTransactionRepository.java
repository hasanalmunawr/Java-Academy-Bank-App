package hasanalmunawr.Dev.JavaAcademyBankApp.repository;


import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryTransactionRepository extends CrudRepository<PrimaryTransaction, String> {

}
