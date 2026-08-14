package com.galampoix.bank.infrastructure.web;

import java.util.UUID;

/**
 * Corps de requête JSON pour la demande de virement entre deux comptes.
 *
 * @param destinationAccountId identifiant du compte à créditer
 * @param montantCentimes      montant du virement, en centimes
 */
public record TransferRequest(UUID destinationAccountId, long montantCentimes) {
}
