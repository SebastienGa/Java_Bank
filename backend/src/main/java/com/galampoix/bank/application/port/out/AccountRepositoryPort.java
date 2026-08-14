package com.galampoix.bank.application.port.out;

import com.galampoix.bank.domain.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Port de sortie décrivant les opérations de persistance disponibles pour
 * les comptes bancaires. Les cas d'utilisation dépendent uniquement de
 * cette abstraction, l'implémentation concrète (JPA ou autre) étant fournie
 * par un adaptateur d'infrastructure.
 */
public interface AccountRepositoryPort {

    /**
     * Recherche un compte par son identifiant.
     *
     * @param id identifiant du compte recherché
     * @return le compte correspondant, ou {@link Optional#empty()} s'il n'existe pas
     */
    Optional<Account> findById(UUID id);

    /**
     * Retourne l'ensemble des comptes existants.
     *
     * @return la liste de tous les comptes
     */
    List<Account> findAll();

    /**
     * Retourne l'ensemble des comptes détenus par un client donné.
     *
     * @param clientId identifiant du client titulaire
     * @return la liste des comptes du client, vide s'il n'en possède aucun
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
