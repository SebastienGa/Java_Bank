package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.application.port.out.LoanRepositoryPort;
import com.galampoix.bank.domain.model.Loan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Implémentation JPA du port de sortie {@link LoanRepositoryPort}.
 * <p>
 * Délègue l'accès aux données à {@link SpringDataLoanRepository} et
 * convertit les entités JPA en objets de domaine via {@link LoanMapper}.
 */
@Repository
public class JpaLoanRepositoryAdapter implements LoanRepositoryPort {

    private final SpringDataLoanRepository springDataLoanRepository;

    public JpaLoanRepositoryAdapter(SpringDataLoanRepository springDataLoanRepository) {
        this.springDataLoanRepository = springDataLoanRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> findByClientId(UUID clientId) {
        return springDataLoanRepository.findByClientId(clientId).stream()
                .map(LoanMapper::toDomain)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> findAll() {
        return springDataLoanRepository.findAll().stream()
                .map(LoanMapper::toDomain)
                .toList();
    }
}
