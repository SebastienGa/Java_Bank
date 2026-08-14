package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.domain.model.Account;
import com.galampoix.bank.domain.model.Client;

/**
 * Convertit un {@link Account} du domaine en {@link AccountResponse}
 * destiné à être exposé par l'API.
 */
public final class AccountWebMapper {

    private AccountWebMapper() {
    }

    /**
     * Construit la représentation web d'un compte, enrichie des
     * informations du client titulaire.
     *
     * @param account compte du domaine à convertir
     * @param client  client titulaire du compte
     * @return la représentation web du compte
     */
    public static AccountResponse toResponse(Account account, Client client) {
        return new AccountResponse(account.id(), client.prenom(), client.nom(), account.soldeCentimes());
    }
}
