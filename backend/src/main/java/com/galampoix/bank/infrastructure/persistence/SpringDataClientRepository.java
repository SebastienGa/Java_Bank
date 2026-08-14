package com.galampoix.bank.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Répertoire Spring Data JPA pour l'entité {@link ClientEntity}.
 * <p>
 * Fournit uniquement les opérations CRUD standard héritées de
 * {@link JpaRepository}.
 */
public interface SpringDataClientRepository extends JpaRepository<ClientEntity, UUID> {
}
