package com.galampoix.bank.domain.exception;

import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {

    private final UUID accountId;
    private final long montantCentimes;

    public InsufficientFundsException(UUID accountId, long montantCentimes) {
        super("Solde insuffisant sur le compte " + accountId + " pour un débit de " + montantCentimes + " centimes");
        this.accountId = accountId;
        this.montantCentimes = montantCentimes;
    }

    public UUID accountId() {
        return accountId;
    }

    public long montantCentimes() {
        return montantCentimes;
    }
}
