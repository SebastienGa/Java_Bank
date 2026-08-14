package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.domain.model.Client;

/**
 * Convertit un {@link Client} du domaine vers/depuis son entité JPA
 * {@link ClientEntity}.
 */
public final class ClientMapper {

    private ClientMapper() {
    }

    /**
     * Convertit une entité JPA en objet de domaine.
     *
     * @param entity entité de client persistée
     * @return le client du domaine correspondant
     */
    public static Client toDomain(ClientEntity entity) {
        return new Client(entity.getId(), entity.getPrenom(), entity.getNom(), entity.getEmail());
    }

    /**
     * Convertit un objet de domaine en entité JPA prête à être persistée.
     *
     * @param client client du domaine à convertir
     * @return l'entité JPA correspondante
     */
    public static ClientEntity toEntity(Client client) {
        return new ClientEntity(client.id(), client.prenom(), client.nom(), client.email());
    }
}
