package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.application.port.out.AccountRepositoryPort;
import com.galampoix.bank.domain.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implémentation JPA du port de sortie {@link AccountRepositoryPort}.
 * <p>
 * Délègue l'accès aux données à {@link SpringDataAccountRepository} et
 * convertit les entités JPA en objets de domaine via {@link AccountMapper}.
 */
@Repository
public class JpaAccountRepositoryAdapter implements AccountRepositoryPort {

    private final SpringDataAccountRepository springDataAccountRepository;

    public JpaAccountRepositoryAdapter(SpringDataAccountRepository springDataAccountRepository) {
        this.springDataAccountRepository = springDataAccountRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Account> findById(UUID id) {
        return springDataAccountRepository.findById(id)
                .map(AccountMapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Account> findAll() {
        return springDataAccountRepository.findAll().stream()
                .map(AccountMapper::toDomain)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Account> findByClientId(UUID clientId) {
        return springDataAccountRepository.findByClientId(clientId).stream()
                .map(AccountMapper::toDomain)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account save(Account account) {
        AccountEntity saved = springDataAccountRepository.save(AccountMapper.toEntity(account));
        return AccountMapper.toDomain(saved);
    }
}
