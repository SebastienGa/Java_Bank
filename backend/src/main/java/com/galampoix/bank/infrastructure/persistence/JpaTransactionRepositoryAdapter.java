package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.application.port.out.TransactionRepositoryPort;
import com.galampoix.bank.domain.model.Transaction;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaTransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final SpringDataTransactionRepository springDataTransactionRepository;

    public JpaTransactionRepositoryAdapter(SpringDataTransactionRepository springDataTransactionRepository) {
        this.springDataTransactionRepository = springDataTransactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity saved = springDataTransactionRepository.save(TransactionMapper.toEntity(transaction));
        return TransactionMapper.toDomain(saved);
    }

    @Override
    public List<Transaction> findByAccountId(UUID accountId) {
        return springDataTransactionRepository.findByAccountIdOrderByDateOperationDesc(accountId).stream()
                .map(TransactionMapper::toDomain)
                .toList();
    }

    @Override
    public List<Transaction> findRecent(int limit) {
        return springDataTransactionRepository.findAllByOrderByDateOperationDesc(Limit.of(limit)).stream()
                .map(TransactionMapper::toDomain)
                .toList();
    }
}
