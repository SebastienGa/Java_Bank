package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.AccountRepositoryPort;
import com.galampoix.bank.domain.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListAccountsForClientUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public ListAccountsForClientUseCase(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    public List<Account> execute(UUID clientId) {
        return accountRepositoryPort.findByClientId(clientId);
    }
}
