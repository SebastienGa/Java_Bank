package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.LoanRepositoryPort;
import com.galampoix.bank.domain.model.Loan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListLoansUseCase {

    private final LoanRepositoryPort loanRepositoryPort;

    public ListLoansUseCase(LoanRepositoryPort loanRepositoryPort) {
        this.loanRepositoryPort = loanRepositoryPort;
    }

    public List<Loan> execute() {
        return loanRepositoryPort.findAll();
    }
}
