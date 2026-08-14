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
 * Cas d'utilisation : virer une somme d'argent d'un compte vers un autre.
 * <p>
 * Ne dépend que du port de sortie {@link AccountRepositoryPort}, jamais
 * d'une implémentation concrète (JPA, etc.). Le débit et le crédit sont
 * effectués dans une même transaction afin de garantir la cohérence des
 * soldes.
 */
@Service
public class TransferMoneyUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public TransferMoneyUseCase(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    /**
     * Effectue un virement entre deux comptes.
     * <p>
     * Débite le compte source du montant indiqué puis crédite le compte
     * destination du même montant, de manière transactionnelle.
     *
     * @param sourceAccountId      identifiant du compte à débiter
     * @param destinationAccountId identifiant du compte à créditer
     * @param montantCentimes      montant du virement, en centimes ;
     *                              doit être strictement positif
     * @throws AccountNotFoundException      si le compte source ou le compte
     *                                        destination n'existe pas
     * @throws SameAccountTransferException   si le compte source et le compte
     *                                        destination sont identiques
     * @throws InsufficientFundsException     si le solde du compte source est
     *                                        insuffisant pour couvrir le débit
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
