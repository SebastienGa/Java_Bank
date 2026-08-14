package com.galampoix.bank.application.port.out;

import com.galampoix.bank.domain.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Port de sortie exposant les opérations de lecture nécessaires à la
 * couche application pour manipuler des {@link Client}.
 * <p>
 * Cette interface fait partie du domaine/application et ne dépend
 * d'aucune technologie de persistance particulière ; son implémentation
 * concrète (JPA ou autre) réside dans la couche infrastructure.
 */
public interface ClientRepositoryPort {

    /**
     * Recherche un client par son identifiant.
     *
     * @param id identifiant du client recherché
     * @return le client correspondant, ou {@link Optional#empty()} si aucun
     *         client ne possède cet identifiant
     */
    Optional<Client> findById(UUID id);

    /**
     * Retourne l'ensemble des clients existants.
     *
     * @return la liste de tous les clients, éventuellement vide
     */
    List<Client> findAll();
}
