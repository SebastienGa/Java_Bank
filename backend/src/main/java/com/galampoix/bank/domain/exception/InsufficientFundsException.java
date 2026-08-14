package com.galampoix.bank.domain.exception;

import java.util.UUID;

/**
 * Levée lorsqu'un débit ne peut pas être effectué car le solde du compte
 * source est insuffisant pour couvrir le montant demandé.
 */
public class InsufficientFundsException extends RuntimeException {

    private final UUID accountId;
    private final long montantCentimes;

    /**
     * Crée l'exception pour un débit refusé faute de solde suffisant.
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
     * Retourne l'identifiant du compte dont le solde est insuffisant.
     *
     * @return l'identifiant du compte concerné
     */
    public UUID accountId() {
        return accountId;
    }

    /**
     * Retourne le montant du débit qui a été refusé.
     *
     * @return le montant du débit refusé, en centimes
     */
    public long montantCentimes() {
        return montantCentimes;
    }
}
