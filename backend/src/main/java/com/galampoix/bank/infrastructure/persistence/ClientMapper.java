package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.domain.model.Client;

public final class ClientMapper {

    private ClientMapper() {
    }

    public static Client toDomain(ClientEntity entity) {
        return new Client(entity.getId(), entity.getPrenom(), entity.getNom(), entity.getEmail());
    }

    public static ClientEntity toEntity(Client client) {
        return new ClientEntity(client.id(), client.prenom(), client.nom(), client.email());
    }
}
