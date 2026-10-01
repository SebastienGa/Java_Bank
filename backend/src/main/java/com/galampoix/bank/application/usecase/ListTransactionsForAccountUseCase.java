package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.AccountRepositoryPort;
import com.galampoix.bank.application.port.out.TransactionRepositoryPort;
import com.galampoix.bank.domain.exception.AccountNotFoundException;
import com.galampoix.bank.domain.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListTransactionsForAccountUseCase {

    private final AccountRepositoryPort accountRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;

    public ListTransactionsForAccountUseCase(AccountRepositoryPort accountRepositoryPort,
                                             TransactionRepositoryPort transactionRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.transactionRepositoryPort = transactionRepositoryPort;
    }

    public List<Transaction> execute(UUID accountId) {
        if (accountRepositoryPort.findById(accountId).isEmpty()) {
            throw new AccountNotFoundException(accountId);
        }
        return transactionRepositoryPort.findByAccountId(accountId);
    }
}
