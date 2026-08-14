package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.AccountRepositoryPort;
import com.galampoix.bank.domain.exception.AccountNotFoundException;
import com.galampoix.bank.domain.exception.InsufficientFundsException;
import com.galampoix.bank.domain.exception.SameAccountTransferException;
import com.galampoix.bank.domain.model.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Cas d'utilisation : effectuer un virement d'un compte source vers un
 * compte destination.
 * <p>
 * Débite le compte source et crédite le compte destination du même
 * montant, de façon atomique grâce à {@link Transactional}. Ne dépend que
 * du port de sortie {@link AccountRepositoryPort}, jamais d'une
 * implémentation concrète (JPA, etc.).
 */
@Service
public class TransferMoneyUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public TransferMoneyUseCase(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    /**
     * Effectue un virement entre deux comptes.
     *
     * @param sourceAccountId      identifiant du compte à débiter
     * @param destinationAccountId identifiant du compte à créditer
     * @param montantCentimes      montant du virement, en centimes
     * @throws AccountNotFoundException     si le compte source ou le compte destination est introuvable
     * @throws SameAccountTransferException si le compte source et le compte destination sont identiques
     * @throws InsufficientFundsException   si le solde du compte source est insuffisant pour couvrir le virement
     */
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
