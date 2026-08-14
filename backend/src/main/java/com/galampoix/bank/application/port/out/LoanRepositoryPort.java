package com.galampoix.bank.application.port.out;

import com.galampoix.bank.domain.model.Loan;

import java.util.List;
import java.util.UUID;

/**
 * Port de sortie décrivant les opérations de persistance disponibles pour
 * les prêts. Les cas d'utilisation dépendent uniquement de cette
 * abstraction, l'implémentation concrète (JPA ou autre) étant fournie par
 * un adaptateur d'infrastructure.
 */
public interface LoanRepositoryPort {

    /**
     * Retourne l'ensemble des prêts détenus par un client donné.
     *
     * @param clientId identifiant du client titulaire
     * @return la liste des prêts du client, vide s'il n'en possède aucun
     */
    List<Loan> findByClientId(UUID clientId);

    /**
     * Retourne l'ensemble des prêts existants.
     *
     * @return la liste de tous les prêts
     */
    List<Loan> findAll();
}
