package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.LoanRepositoryPort;
import com.galampoix.bank.domain.model.Loan;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Cas d'utilisation : lister l'ensemble des prêts.
 * <p>
 * Ne dépend que du port de sortie {@link LoanRepositoryPort}, jamais
 * d'une implémentation concrète (JPA, etc.).
 */
@Service
public class ListLoansUseCase {

    private final LoanRepositoryPort loanRepositoryPort;

    public ListLoansUseCase(LoanRepositoryPort loanRepositoryPort) {
        this.loanRepositoryPort = loanRepositoryPort;
    }

    /**
     * Exécute le cas d'utilisation.
     *
     * @return la liste de tous les prêts, éventuellement vide
     */
    public List<Loan> execute() {
        return loanRepositoryPort.findAll();
    }
}
