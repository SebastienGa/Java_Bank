package com.galampoix.bank.domain.exception;

import java.util.UUID;

/**
 * Exception levée lorsqu'aucun compte ne correspond à l'identifiant recherché.
 */
public class AccountNotFoundException extends RuntimeException {

    private final UUID accountId;

    /**
     * Crée l'exception pour le compte introuvable indiqué.
     *
     * @param accountId identifiant du compte qui n'a pas été trouvé
     */
    public AccountNotFoundException(UUID accountId) {
        super("Compte introuvable : " + accountId);
        this.accountId = accountId;
    }

    /**
     * Retourne l'identifiant du compte qui n'a pas été trouvé.
     *
     * @return identifiant du compte introuvable
     */
    public UUID accountId() {
        return accountId;
    }
}
