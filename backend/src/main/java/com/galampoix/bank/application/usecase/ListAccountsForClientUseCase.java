package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.AccountRepositoryPort;
import com.galampoix.bank.domain.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Cas d'utilisation : lister les comptes détenus par un client donné.
 * <p>
 * Ne dépend que du port de sortie {@link AccountRepositoryPort}, jamais
 * d'une implémentation concrète (JPA, etc.).
 */
@Service
public class ListAccountsForClientUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public ListAccountsForClientUseCase(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    /**
     * Recherche les comptes appartenant à un client.
     *
     * @param clientId identifiant du client titulaire des comptes
     * @return la liste des comptes du client, éventuellement vide
     */
    public List<Account> execute(UUID clientId) {
        return accountRepositoryPort.findByClientId(clientId);
    }
}
