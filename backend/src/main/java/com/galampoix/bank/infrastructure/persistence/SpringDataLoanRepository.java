package com.galampoix.bank.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Répertoire Spring Data JPA pour l'entité {@link LoanEntity}.
 */
public interface SpringDataLoanRepository extends JpaRepository<LoanEntity, UUID> {

    /**
     * Recherche les prêts appartenant à un client.
     *
     * @param clientId identifiant du client titulaire des prêts
     * @return la liste des entités de prêt du client, éventuellement vide
     */
    List<LoanEntity> findByClientId(UUID clientId);
}
