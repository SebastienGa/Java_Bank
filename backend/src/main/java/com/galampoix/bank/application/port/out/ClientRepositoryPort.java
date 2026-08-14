package com.galampoix.bank.application.port.out;

import com.galampoix.bank.domain.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Port de sortie décrivant les opérations de persistance disponibles pour
 * les clients de la banque. Les cas d'utilisation dépendent uniquement de
 * cette abstraction, l'implémentation concrète (JPA ou autre) étant fournie
 * par un adaptateur d'infrastructure.
 */
public interface ClientRepositoryPort {

    /**
     * Recherche un client par son identifiant.
     *
     * @param id identifiant du client recherché
     * @return le client correspondant, ou {@link Optional#empty()} s'il n'existe pas
     */
    Optional<Client> findById(UUID id);

    /**
     * Retourne l'ensemble des clients existants.
     *
     * @return la liste de tous les clients
     */
    List<Client> findAll();
}
