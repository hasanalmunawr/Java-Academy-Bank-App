package hasanalmunawr.Dev.OnlineWallet.service.impl;

import hasanalmunawr.Dev.OnlineWallet.entity.PrimaryTransaction;
import hasanalmunawr.Dev.OnlineWallet.entity.UserEntity;
import hasanalmunawr.Dev.OnlineWallet.repository.PrimaryTransactionRepository;
import hasanalmunawr.Dev.OnlineWallet.service.PrimaryTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrimaryTransactionServiceImpl implements PrimaryTransactionService {

    private final PrimaryTransactionRepository transactionRepository;

    @Override
    public PrimaryTransaction savingTransaction(PrimaryTransaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<PrimaryTransaction> getHistoryTransactions(Authentication currentUser) {
         UserEntity user = (UserEntity) currentUser.getPrincipal();

        var primaryAccount = user.getPrimaryAccount();

        return transactionRepository.findAllByPrimaryAccount(primaryAccount);
    }
}
