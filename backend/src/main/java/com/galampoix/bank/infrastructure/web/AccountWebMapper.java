package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.domain.model.Account;

/**
 * Traduit le modèle de domaine vers le DTO exposé par l'API REST.
 * Le domaine ne doit jamais fuiter tel quel vers l'extérieur.
 */
public final class AccountWebMapper {

    private AccountWebMapper() {
    }

    public static AccountResponse toResponse(Account account) {
        return new AccountResponse(account.id(), account.titulaire(), account.soldeCentimes());
    }
}
