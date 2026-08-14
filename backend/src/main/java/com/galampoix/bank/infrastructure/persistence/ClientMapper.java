package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.domain.model.Client;

/**
 * Convertit les clients entre leur représentation métier
 * ({@link Client}) et leur représentation de persistance JPA
 * ({@link ClientEntity}).
 */
public final class ClientMapper {

    private ClientMapper() {
    }

    /**
     * Convertit une entité JPA en objet de domaine.
     *
     * @param entity entité de client issue de la persistance
     * @return le client correspondant sous forme d'objet de domaine
     */
    public static Client toDomain(ClientEntity entity) {
        return new Client(entity.getId(), entity.getPrenom(), entity.getNom(), entity.getEmail());
    }

    /**
     * Convertit un objet de domaine en entité JPA.
     *
     * @param client client du domaine à convertir
     * @return l'entité de client prête à être persistée
     */
    public static ClientEntity toEntity(Client client) {
        return new ClientEntity(client.id(), client.prenom(), client.nom(), client.email());
    }
}
