package com.galampoix.bank.application.port.out;

import com.galampoix.bank.domain.model.Loan;

import java.util.List;
import java.util.UUID;

public interface LoanRepositoryPort {

    List<Loan> findByClientId(UUID clientId);

    List<Loan> findAll();
}
