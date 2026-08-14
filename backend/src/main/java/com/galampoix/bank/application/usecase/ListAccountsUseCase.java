package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.AccountRepositoryPort;
import com.galampoix.bank.domain.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Cas d'utilisation : lister l'ensemble des comptes.
 * <p>
 * Ne dépend que du port de sortie {@link AccountRepositoryPort}, jamais
 * d'une implémentation concrète (JPA, etc.). L'annotation {@code @Service}
 * est la seule concession au framework, uniquement pour bénéficier de
 * l'injection de dépendances de Spring.
 */
@Service
public class ListAccountsUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public ListAccountsUseCase(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    /**
     * Exécute le cas d'utilisation.
     *
     * @return la liste de tous les comptes, éventuellement vide
     */
    public List<Account> execute() {
        return accountRepositoryPort.findAll();
    }
}
