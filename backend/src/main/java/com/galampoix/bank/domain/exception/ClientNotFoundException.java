package com.galampoix.bank.domain.exception;

import java.util.UUID;

/**
 * Levée lorsqu'un client identifié par son {@link UUID} est introuvable.
 */
public class ClientNotFoundException extends RuntimeException {

    private final UUID clientId;

    /**
     * Crée l'exception pour le client introuvable dont l'identifiant est fourni.
     *
     * @param clientId identifiant du client qui n'a pas été trouvé
     */
    public ClientNotFoundException(UUID clientId) {
        super("Client introuvable : " + clientId);
        this.clientId = clientId;
    }

    /**
     * Retourne l'identifiant du client introuvable à l'origine de l'exception.
     *
     * @return l'identifiant du client recherché
     */
    public UUID clientId() {
        return clientId;
    }
}
