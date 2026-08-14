package com.galampoix.bank.domain.exception;

import java.util.UUID;

/**
 * Exception levée lorsqu'un virement est tenté entre un compte source et
 * un compte destination identiques.
 */
public class SameAccountTransferException extends RuntimeException {

    private final UUID accountId;

    /**
     * Crée l'exception pour le virement invalide indiqué.
     *
     * @param accountId identifiant du compte utilisé à la fois comme source
     *                  et comme destination du virement
     */
    public SameAccountTransferException(UUID accountId) {
        super("Impossible d'effectuer un virement vers le même compte : " + accountId);
        this.accountId = accountId;
    }

    /**
     * Retourne l'identifiant du compte concerné par le virement invalide.
     *
     * @return identifiant du compte source/destination
     */
    public UUID accountId() {
        return accountId;
    }
}
