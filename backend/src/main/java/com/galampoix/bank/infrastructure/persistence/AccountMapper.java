package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.domain.model.Account;

/**
 * Convertit un {@link Account} du domaine vers/depuis son entité JPA
 * {@link AccountEntity}.
 */
public final class AccountMapper {

    private AccountMapper() {
    }

    /**
     * Convertit une entité JPA en objet de domaine.
     *
     * @param entity entité de compte persistée
     * @return le compte du domaine correspondant
     */
    public static Account toDomain(AccountEntity entity) {
        return new Account(entity.getId(), entity.getClient().getId(), entity.getSoldeCentimes());
    }

    /**
     * Convertit un objet de domaine en entité JPA prête à être persistée.
     * <p>
     * L'entité client associée n'est constituée que de son identifiant :
     * elle sert uniquement de référence pour la relation JPA et ne doit
     * pas être utilisée pour lire les autres attributs du client.
     *
     * @param account compte du domaine à convertir
     * @return l'entité JPA correspondante
     */
    public static AccountEntity toEntity(Account account) {
        ClientEntity clientRef = new ClientEntity();
        clientRef.setId(account.clientId());
        return new AccountEntity(account.id(), clientRef, account.soldeCentimes());
    }
}
