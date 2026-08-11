package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.domain.model.Account;

/**
 * Traduit entre le modèle de domaine et l'entité JPA, dans les deux sens.
 * C'est la seule classe qui a le droit de connaître à la fois le domaine
 * et la technologie de persistance.
 */
public final class AccountMapper {

    private AccountMapper() {
    }

    public static Account toDomain(AccountEntity entity) {
        return new Account(entity.getId(), entity.getTitulaire(), entity.getSoldeCentimes());
    }

    public static AccountEntity toEntity(Account account) {
        return new AccountEntity(account.id(), account.titulaire(), account.soldeCentimes());
    }
}
