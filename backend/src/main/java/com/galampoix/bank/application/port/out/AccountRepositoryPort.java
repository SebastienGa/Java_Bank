package com.galampoix.bank.application.port.out;

import com.galampoix.bank.domain.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Port de sortie (driven port) : contrat que l'application attend d'un
 * adaptateur de persistance, sans jamais dépendre de sa technologie
 * (JPA, JDBC, mémoire, etc.).
 */
public interface AccountRepositoryPort {

    Optional<Account> findById(UUID id);

    List<Account> findAll();

    Account save(Account account);
}
