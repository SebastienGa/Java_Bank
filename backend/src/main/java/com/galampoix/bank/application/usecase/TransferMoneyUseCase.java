package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.AccountRepositoryPort;
import com.galampoix.bank.domain.exception.AccountNotFoundException;
import com.galampoix.bank.domain.exception.InsufficientFundsException;
import com.galampoix.bank.domain.exception.SameAccountTransferException;
import com.galampoix.bank.domain.model.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransferMoneyUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public TransferMoneyUseCase(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Transactional
    public void execute(UUID sourceAccountId, UUID destinationAccountId, long montantCentimes) {
        Account source = accountRepositoryPort.findById(sourceAccountId)
                .orElseThrow(() -> new AccountNotFoundException(sourceAccountId));
        Account destination = accountRepositoryPort.findById(destinationAccountId)
                .orElseThrow(() -> new AccountNotFoundException(destinationAccountId));

        if (source.id().equals(destination.id())) {
            throw new SameAccountTransferException(sourceAccountId);
        }

        Account sourceDebite;
        try {
            sourceDebite = source.debiter(montantCentimes);
        } catch (IllegalStateException e) {
            throw new InsufficientFundsException(sourceAccountId, montantCentimes);
        }
        Account destinationCreditee = destination.crediter(montantCentimes);

        accountRepositoryPort.save(sourceDebite);
        accountRepositoryPort.save(destinationCreditee);
    }
}
