package com.galampoix.bank.infrastructure.web;

import java.time.Instant;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        UUID accountId,
        UUID counterpartAccountId,
        String libelle,
        String categorie,
        long montantCentimes,
        Instant dateOperation
) {
}
