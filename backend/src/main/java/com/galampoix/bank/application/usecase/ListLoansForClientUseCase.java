package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.LoanRepositoryPort;
import com.galampoix.bank.domain.model.Loan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListLoansForClientUseCase {

    private final LoanRepositoryPort loanRepositoryPort;

    public ListLoansForClientUseCase(LoanRepositoryPort loanRepositoryPort) {
        this.loanRepositoryPort = loanRepositoryPort;
    }

    public List<Loan> execute(UUID clientId) {
        return loanRepositoryPort.findByClientId(clientId);
    }
}
