package com.galampoix.bank.domain.exception;

import java.util.UUID;

/**
 * Exception levée lorsqu'un débit ne peut pas être effectué car le solde
 * du compte est insuffisant pour couvrir le montant demandé.
 */
public class InsufficientFundsException extends RuntimeException {

    private final UUID accountId;
    private final long montantCentimes;

    /**
     * Crée l'exception pour le débit refusé indiqué.
     *
     * @param accountId       identifiant du compte dont le solde est insuffisant
     * @param montantCentimes montant du débit refusé, en centimes
     */
    public InsufficientFundsException(UUID accountId, long montantCentimes) {
        super("Solde insuffisant sur le compte " + accountId + " pour un débit de " + montantCentimes + " centimes");
        this.accountId = accountId;
        this.montantCentimes = montantCentimes;
    }

    /**
     * Retourne l'identifiant du compte concerné par le débit refusé.
     *
     * @return identifiant du compte au solde insuffisant
     */
    public UUID accountId() {
        return accountId;
    }

    /**
     * Retourne le montant du débit qui n'a pas pu être effectué.
     *
     * @return montant du débit refusé, en centimes
     */
    public long montantCentimes() {
        return montantCentimes;
    }
}
