package com.galampoix.bank.domain.exception;

import java.util.UUID;

public class SameAccountTransferException extends RuntimeException {

    private final UUID accountId;

    public SameAccountTransferException(UUID accountId) {
        super("Impossible d'effectuer un virement vers le même compte : " + accountId);
        this.accountId = accountId;
    }

    public UUID accountId() {
        return accountId;
    }
}
