package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.domain.model.Account;

/**
 * Convertit les comptes entre leur représentation métier
 * ({@link Account}) et leur représentation de persistance JPA
 * ({@link AccountEntity}).
 */
public final class AccountMapper {

    private AccountMapper() {
    }

    /**
     * Convertit une entité JPA en objet de domaine.
     *
     * @param entity entité de compte issue de la persistance
     * @return le compte correspondant sous forme d'objet de domaine
     */
    public static Account toDomain(AccountEntity entity) {
        return new Account(entity.getId(), entity.getClient().getId(), entity.getSoldeCentimes());
    }

    /**
     * Convertit un objet de domaine en entité JPA.
     * <p>
     * L'entité {@link ClientEntity} associée n'est constituée que de son
     * identifiant (référence), sans charger le client complet.
     *
     * @param account compte du domaine à convertir
     * @return l'entité de compte prête à être persistée
     */
    public static AccountEntity toEntity(Account account) {
        ClientEntity clientRef = new ClientEntity();
        clientRef.setId(account.clientId());
        return new AccountEntity(account.id(), clientRef, account.soldeCentimes());
    }
}
