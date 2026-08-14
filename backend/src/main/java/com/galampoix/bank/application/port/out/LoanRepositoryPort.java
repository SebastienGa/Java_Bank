package com.galampoix.bank.application.port.out;

import com.galampoix.bank.domain.model.Loan;

import java.util.List;
import java.util.UUID;

/**
 * Port de sortie exposant les opérations de lecture nécessaires à la
 * couche application pour manipuler des {@link Loan}.
 * <p>
 * Cette interface fait partie du domaine/application et ne dépend
 * d'aucune technologie de persistance particulière ; son implémentation
 * concrète (JPA ou autre) réside dans la couche infrastructure.
 */
public interface LoanRepositoryPort {

    /**
     * Retourne l'ensemble des prêts détenus par un client donné.
     *
     * @param clientId identifiant du client titulaire des prêts
     * @return la liste des prêts du client, éventuellement vide
     */
    List<Loan> findByClientId(UUID clientId);

    /**
     * Retourne l'ensemble des prêts existants.
     *
     * @return la liste de tous les prêts, éventuellement vide
     */
    List<Loan> findAll();
}
