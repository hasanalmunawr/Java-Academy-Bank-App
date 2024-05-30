package hasanalmunawr.Dev.OnlineWallet.service.impl;

import hasanalmunawr.Dev.OnlineWallet.entity.PrimaryAccount;
import hasanalmunawr.Dev.OnlineWallet.repository.PrimaryAccountRepository;
import hasanalmunawr.Dev.OnlineWallet.service.AccountService;
import hasanalmunawr.Dev.OnlineWallet.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {

    private final PrimaryAccountRepository accountRepository;

    @Override
    public PrimaryAccount createPrimaryAccount() {
        PrimaryAccount account = PrimaryAccount.builder()
                .accountNumber(UserUtils.generateAccountNumber())
                .accountBalance(0L)
                .build();
        return accountRepository.save(account);
    }


}
