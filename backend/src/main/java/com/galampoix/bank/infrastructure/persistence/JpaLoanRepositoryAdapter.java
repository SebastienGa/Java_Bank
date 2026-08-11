package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.application.port.out.LoanRepositoryPort;
import com.galampoix.bank.domain.model.Loan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaLoanRepositoryAdapter implements LoanRepositoryPort {

    private final SpringDataLoanRepository springDataLoanRepository;

    public JpaLoanRepositoryAdapter(SpringDataLoanRepository springDataLoanRepository) {
        this.springDataLoanRepository = springDataLoanRepository;
    }

    @Override
    public List<Loan> findByClientId(UUID clientId) {
        return springDataLoanRepository.findByClientId(clientId).stream()
                .map(LoanMapper::toDomain)
                .toList();
    }

    @Override
    public List<Loan> findAll() {
        return springDataLoanRepository.findAll().stream()
                .map(LoanMapper::toDomain)
                .toList();
    }
}
