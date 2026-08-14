package com.galampoix.bank.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Répertoire Spring Data JPA pour l'entité {@link ClientEntity}.
 */
public interface SpringDataClientRepository extends JpaRepository<ClientEntity, UUID> {
}
