package hasanalmunawr.Dev.JavaAcademyBankApp.service.impl;

import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryTransaction;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.PrimaryTransactionRepository;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.PrimaryTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrimaryTransactionServiceImpl implements PrimaryTransactionService {

    private final PrimaryTransactionRepository transactionRepository;

    @Override
    public PrimaryTransaction savingTransaction(PrimaryTransaction transaction) {
        return transactionRepository.save(transaction);
    }
}
