package com.galampoix.bank.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository technique Spring Data, cantonné à l'infrastructure.
 * N'est jamais utilisé directement en dehors de {@link JpaAccountRepositoryAdapter}.
 */
public interface SpringDataAccountRepository extends JpaRepository<AccountEntity, UUID> {
}
