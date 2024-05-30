package hasanalmunawr.Dev.JavaAcademyBankApp.repository;

import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryAccount;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrimaryAccountRepository extends CrudRepository<PrimaryAccount, String> {

    Optional<PrimaryAccount> findByAccountNumber(Integer accountNumber);

}
