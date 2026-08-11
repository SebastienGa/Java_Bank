package com.galampoix.bank.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataClientRepository extends JpaRepository<ClientEntity, UUID> {
}
