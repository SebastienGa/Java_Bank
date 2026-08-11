package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.application.port.out.AccountRepositoryPort;
import com.galampoix.bank.domain.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adaptateur de sortie : implémente le port {@link AccountRepositoryPort}
 * attendu par l'application, en s'appuyant sur Spring Data JPA. C'est ici,
 * et uniquement ici, que le domaine est mis en relation avec la
 * technologie de persistance (via {@link AccountMapper}).
 */
@Repository
public class JpaAccountRepositoryAdapter implements AccountRepositoryPort {

    private final SpringDataAccountRepository springDataAccountRepository;

    public JpaAccountRepositoryAdapter(SpringDataAccountRepository springDataAccountRepository) {
        this.springDataAccountRepository = springDataAccountRepository;
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return springDataAccountRepository.findById(id)
                .map(AccountMapper::toDomain);
    }

    @Override
    public List<Account> findAll() {
        return springDataAccountRepository.findAll().stream()
                .map(AccountMapper::toDomain)
                .toList();
    }

    @Override
    public Account save(Account account) {
        AccountEntity saved = springDataAccountRepository.save(AccountMapper.toEntity(account));
        return AccountMapper.toDomain(saved);
    }
}
