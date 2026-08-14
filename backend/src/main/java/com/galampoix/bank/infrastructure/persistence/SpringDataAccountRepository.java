package com.galampoix.bank.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Répertoire Spring Data JPA pour l'entité {@link AccountEntity}.
 * <p>
 * Fournit les opérations CRUD standard héritées de {@link JpaRepository},
 * ainsi qu'une méthode de recherche dérivée spécifique au domaine.
 */
public interface SpringDataAccountRepository extends JpaRepository<AccountEntity, UUID> {

    /**
     * Recherche les comptes appartenant à un client donné.
     *
     * @param clientId identifiant du client titulaire
     * @return la liste des entités de compte associées à ce client
     */
    List<AccountEntity> findByClientId(UUID clientId);
}
