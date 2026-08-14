package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.domain.model.Account;
import com.galampoix.bank.domain.model.Client;

/**
 * Convertit un compte du domaine, associé à son client titulaire, en
 * {@link AccountResponse} destiné à l'API REST.
 */
public final class AccountWebMapper {

    private AccountWebMapper() {
    }

    /**
     * Construit la représentation JSON d'un compte à partir du domaine.
     *
     * @param account compte du domaine à exposer
     * @param client  client titulaire du compte
     * @return la réponse REST correspondante
     */
    public static AccountResponse toResponse(Account account, Client client) {
        return new AccountResponse(account.id(), client.prenom(), client.nom(), account.soldeCentimes());
    }
}
