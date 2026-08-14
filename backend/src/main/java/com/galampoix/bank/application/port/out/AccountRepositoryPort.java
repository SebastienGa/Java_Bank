package com.galampoix.bank.application.port.out;

import com.galampoix.bank.domain.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Port de sortie exposant les opérations de persistance nécessaires à la
 * couche application pour manipuler des {@link Account}.
 * <p>
 * Cette interface fait partie du domaine/application et ne dépend
 * d'aucune technologie de persistance particulière ; son implémentation
 * concrète (JPA ou autre) réside dans la couche infrastructure.
 */
public interface AccountRepositoryPort {

    /**
     * Recherche un compte par son identifiant.
     *
     * @param id identifiant du compte recherché
     * @return le compte correspondant, ou {@link Optional#empty()} si aucun
     *         compte ne possède cet identifiant
     */
    Optional<Account> findById(UUID id);

    /**
     * Retourne l'ensemble des comptes existants.
     *
     * @return la liste de tous les comptes, éventuellement vide
     */
    List<Account> findAll();

    /**
     * Retourne l'ensemble des comptes détenus par un client donné.
     *
     * @param clientId identifiant du client titulaire des comptes
     * @return la liste des comptes du client, éventuellement vide
     */
    List<Account> findByClientId(UUID clientId);

    /**
     * Enregistre (création ou mise à jour) un compte.
     *
     * @param account compte à sauvegarder
     * @return le compte tel qu'il a été persisté
     */
    Account save(Account account);
}
