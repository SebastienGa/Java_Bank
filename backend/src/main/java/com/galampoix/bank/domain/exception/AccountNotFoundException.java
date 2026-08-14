package com.galampoix.bank.domain.exception;

import java.util.UUID;

/**
 * Levée lorsqu'un compte identifié par son {@link UUID} est introuvable.
 */
public class AccountNotFoundException extends RuntimeException {

    private final UUID accountId;

    /**
     * Crée l'exception pour le compte introuvable dont l'identifiant est fourni.
     *
     * @param accountId identifiant du compte qui n'a pas été trouvé
     */
    public AccountNotFoundException(UUID accountId) {
        super("Compte introuvable : " + accountId);
        this.accountId = accountId;
    }

    /**
     * Retourne l'identifiant du compte introuvable à l'origine de l'exception.
     *
     * @return l'identifiant du compte recherché
     */
    public UUID accountId() {
        return accountId;
    }
}
