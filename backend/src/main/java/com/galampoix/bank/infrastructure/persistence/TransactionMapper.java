package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.domain.model.Transaction;

public final class TransactionMapper {

    private TransactionMapper() {
    }

    public static Transaction toDomain(TransactionEntity entity) {
        return new Transaction(
                entity.getId(),
                entity.getAccountId(),
                entity.getCounterpartAccountId(),
                entity.getLibelle(),
                entity.getCategorie(),
                entity.getMontantCentimes(),
                entity.getDateOperation()
        );
    }

    public static TransactionEntity toEntity(Transaction transaction) {
        return new TransactionEntity(
                transaction.id(),
                transaction.accountId(),
                transaction.counterpartAccountId(),
                transaction.libelle(),
                transaction.categorie(),
                transaction.montantCentimes(),
                transaction.dateOperation()
        );
    }
}
