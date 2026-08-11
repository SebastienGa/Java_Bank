package com.galampoix.bank.domain.exception;

import java.util.UUID;

public class ClientNotFoundException extends RuntimeException {

    private final UUID clientId;

    public ClientNotFoundException(UUID clientId) {
        super("Client introuvable : " + clientId);
        this.clientId = clientId;
    }

    public UUID clientId() {
        return clientId;
    }
}
