package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.domain.model.Transaction;

public final class TransactionWebMapper {

    private TransactionWebMapper() {
    }

    public static TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.id(),
                transaction.accountId(),
                transaction.counterpartAccountId(),
                transaction.libelle(),
                transaction.categorie().name(),
                transaction.montantCentimes(),
                transaction.dateOperation()
        );
    }
}
