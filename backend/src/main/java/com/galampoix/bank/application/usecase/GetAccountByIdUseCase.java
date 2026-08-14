package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.AccountRepositoryPort;
import com.galampoix.bank.domain.model.Account;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Cas d'utilisation : récupérer un compte par son identifiant.
 * <p>
 * Ajouté en complément de {@link ListAccountsUseCase} pour que le
 * endpoint GET /api/accounts/{id} passe lui aussi par la couche
 * application plutôt que d'appeler directement le port de sortie
 * depuis le contrôleur.
 */
@Service
public class GetAccountByIdUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public GetAccountByIdUseCase(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    /**
     * Recherche un compte par son identifiant.
     *
     * @param id identifiant du compte recherché
     * @return le compte correspondant, ou {@link Optional#empty()} si aucun
     *         compte ne possède cet identifiant
     */
    public Optional<Account> execute(UUID id) {
        return accountRepositoryPort.findById(id);
    }
}
