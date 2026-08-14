package com.galampoix.bank.domain.exception;

import java.util.UUID;

/**
 * Levée lorsqu'un virement est demandé entre un compte source et un compte
 * destination identiques, ce qui n'a pas de sens métier.
 */
public class SameAccountTransferException extends RuntimeException {

    private final UUID accountId;

    /**
     * Crée l'exception pour une tentative de virement vers le même compte.
     *
     * @param accountId identifiant du compte utilisé à la fois comme source et destination
     */
    public SameAccountTransferException(UUID accountId) {
        super("Impossible d'effectuer un virement vers le même compte : " + accountId);
        this.accountId = accountId;
    }

    /**
     * Retourne l'identifiant du compte à l'origine du virement invalide.
     *
     * @return l'identifiant du compte concerné
     */
    public UUID accountId() {
        return accountId;
    }
}
