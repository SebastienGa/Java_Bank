package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.LoanRepositoryPort;
import com.galampoix.bank.domain.model.Loan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Cas d'utilisation : lister les prêts appartenant à un client donné.
 * <p>
 * Ne dépend que du port de sortie {@link LoanRepositoryPort}, jamais
 * d'une implémentation concrète (JPA, etc.).
 */
@Service
public class ListLoansForClientUseCase {

    private final LoanRepositoryPort loanRepositoryPort;

    public ListLoansForClientUseCase(LoanRepositoryPort loanRepositoryPort) {
        this.loanRepositoryPort = loanRepositoryPort;
    }

    /**
     * Récupère l'ensemble des prêts détenus par un client.
     *
     * @param clientId identifiant du client titulaire
     * @return la liste des prêts du client, vide s'il n'en possède aucun
     */
    public List<Loan> execute(UUID clientId) {
        return loanRepositoryPort.findByClientId(clientId);
    }
}
