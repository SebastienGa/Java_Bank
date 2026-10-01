package com.galampoix.bank.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record Transaction(
        UUID id,
        UUID accountId,
        UUID counterpartAccountId,
        String libelle,
        TransactionCategory categorie,
        long montantCentimes,
        Instant dateOperation
) {

    public Transaction {
        Objects.requireNonNull(id, "L'identifiant de l'opération est obligatoire");
        Objects.requireNonNull(accountId, "Le compte de l'opération est obligatoire");
        Objects.requireNonNull(categorie, "La catégorie de l'opération est obligatoire");
        Objects.requireNonNull(dateOperation, "La date de l'opération est obligatoire");
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé de l'opération est obligatoire");
        }
        if (montantCentimes == 0) {
            throw new IllegalArgumentException("Le montant d'une opération ne peut pas être nul");
        }
    }

    public static Transaction debit(UUID accountId, UUID counterpartAccountId, String libelle,
                                    TransactionCategory categorie, long montantCentimes, Instant dateOperation) {
        if (montantCentimes <= 0) {
            throw new IllegalArgumentException("Le montant débité doit être positif");
        }
        return new Transaction(UUID.randomUUID(), accountId, counterpartAccountId, libelle, categorie,
                -montantCentimes, dateOperation);
    }

    public static Transaction credit(UUID accountId, UUID counterpartAccountId, String libelle,
                                     TransactionCategory categorie, long montantCentimes, Instant dateOperation) {
        if (montantCentimes <= 0) {
            throw new IllegalArgumentException("Le montant crédité doit être positif");
        }
        return new Transaction(UUID.randomUUID(), accountId, counterpartAccountId, libelle, categorie,
                montantCentimes, dateOperation);
    }
}
