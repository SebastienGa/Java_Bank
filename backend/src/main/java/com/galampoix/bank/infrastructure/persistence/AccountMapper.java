package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.domain.model.Account;

public final class AccountMapper {

    private AccountMapper() {
    }

    public static Account toDomain(AccountEntity entity) {
        return new Account(entity.getId(), entity.getClient().getId(), entity.getSoldeCentimes());
    }

    public static AccountEntity toEntity(Account account) {
        ClientEntity clientRef = new ClientEntity();
        clientRef.setId(account.clientId());
        return new AccountEntity(account.id(), clientRef, account.soldeCentimes());
    }
}
