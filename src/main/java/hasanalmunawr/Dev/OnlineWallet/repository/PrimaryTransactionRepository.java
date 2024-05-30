package hasanalmunawr.Dev.OnlineWallet.repository;


import hasanalmunawr.Dev.OnlineWallet.entity.PrimaryAccount;
import hasanalmunawr.Dev.OnlineWallet.entity.PrimaryTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrimaryTransactionRepository extends CrudRepository<PrimaryTransaction, String> {

    List<PrimaryTransaction> findAllByPrimaryAccount(PrimaryAccount primaryAccount);

}
