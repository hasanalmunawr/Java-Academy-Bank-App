package hasanalmunawr.Dev.JavaAcademyBankApp.repository;


import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryAccount;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrimaryTransactionRepository extends CrudRepository<PrimaryTransaction, String> {

    List<PrimaryTransaction> findAllByPrimaryAccount(PrimaryAccount primaryAccount);

}
