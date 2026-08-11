package com.galampoix.bank.application.port.out;

import com.galampoix.bank.domain.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepositoryPort {

    Optional<Account> findById(UUID id);

    List<Account> findAll();

    List<Account> findByClientId(UUID clientId);

    Account save(Account account);
}
