package com.galampoix.bank.domain.exception;

import java.util.UUID;

/**
 * Exception levée lorsqu'aucun client ne correspond à l'identifiant recherché.
 */
public class ClientNotFoundException extends RuntimeException {

    private final UUID clientId;

    /**
     * Crée l'exception pour le client introuvable indiqué.
     *
     * @param clientId identifiant du client qui n'a pas été trouvé
     */
    public ClientNotFoundException(UUID clientId) {
        super("Client introuvable : " + clientId);
        this.clientId = clientId;
    }

    /**
     * Retourne l'identifiant du client qui n'a pas été trouvé.
     *
     * @return identifiant du client introuvable
     */
    public UUID clientId() {
        return clientId;
    }
}
