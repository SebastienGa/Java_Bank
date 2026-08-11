package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.domain.model.Account;
import com.galampoix.bank.domain.model.Client;

public final class AccountWebMapper {

    private AccountWebMapper() {
    }

    public static AccountResponse toResponse(Account account, Client client) {
        return new AccountResponse(account.id(), client.prenom(), client.nom(), account.soldeCentimes());
    }
}
