package com.galampoix.bank.application.port.out;

import com.galampoix.bank.domain.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepositoryPort {

    Transaction save(Transaction transaction);

    List<Transaction> findByAccountId(UUID accountId);

    List<Transaction> findRecent(int limit);
}
