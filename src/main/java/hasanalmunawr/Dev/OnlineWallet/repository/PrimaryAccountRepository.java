package hasanalmunawr.Dev.OnlineWallet.repository;

import hasanalmunawr.Dev.OnlineWallet.entity.PrimaryAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrimaryAccountRepository extends CrudRepository<PrimaryAccount, String> {

    Optional<PrimaryAccount> findByAccountNumber(Integer accountNumber);

}
