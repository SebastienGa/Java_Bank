package com.galampoix.bank.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Répertoire Spring Data JPA pour l'entité {@link AccountEntity}.
 */
public interface SpringDataAccountRepository extends JpaRepository<AccountEntity, UUID> {

    /**
     * Recherche les comptes appartenant à un client.
     *
     * @param clientId identifiant du client titulaire des comptes
     * @return la liste des entités de compte du client, éventuellement vide
     */
    List<AccountEntity> findByClientId(UUID clientId);
}
