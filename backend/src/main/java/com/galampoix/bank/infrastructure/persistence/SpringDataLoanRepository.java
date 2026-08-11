package com.galampoix.bank.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataLoanRepository extends JpaRepository<LoanEntity, UUID> {

    List<LoanEntity> findByClientId(UUID clientId);
}
