package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.TransactionRepositoryPort;
import com.galampoix.bank.domain.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListRecentTransactionsUseCase {

    static final int LIMITE_MAX = 100;

    private final TransactionRepositoryPort transactionRepositoryPort;

    public ListRecentTransactionsUseCase(TransactionRepositoryPort transactionRepositoryPort) {
        this.transactionRepositoryPort = transactionRepositoryPort;
    }

    public List<Transaction> execute(int limit) {
        if (limit < 1 || limit > LIMITE_MAX) {
            throw new IllegalArgumentException("La limite doit être comprise entre 1 et " + LIMITE_MAX);
        }
        return transactionRepositoryPort.findRecent(limit);
    }
}
