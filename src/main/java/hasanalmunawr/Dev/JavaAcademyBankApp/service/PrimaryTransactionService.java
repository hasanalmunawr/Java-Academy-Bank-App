package hasanalmunawr.Dev.JavaAcademyBankApp.service;


import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryTransaction;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PrimaryTransactionService {

    PrimaryTransaction savingTransaction(PrimaryTransaction transaction);

    List<PrimaryTransaction> getHistoryTransactions(Authentication currentUser);

}
