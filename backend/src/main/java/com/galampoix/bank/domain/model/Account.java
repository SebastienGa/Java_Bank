package com.galampoix.bank.domain.model;

import java.util.Objects;
import java.util.UUID;

public record Account(UUID id, UUID clientId, long soldeCentimes) {

    public Account {
        Objects.requireNonNull(id, "L'identifiant du compte est obligatoire");
        Objects.requireNonNull(clientId, "Le client titulaire du compte est obligatoire");
        if (soldeCentimes < 0) {
            throw new IllegalArgumentException("Le solde d'un compte ne peut pas être négatif");
        }
    }

    public static Account nouveau(UUID id, UUID clientId) {
        return new Account(id, clientId, 0L);
    }

    public Account crediter(long montantCentimes) {
        if (montantCentimes <= 0) {
            throw new IllegalArgumentException("Le montant crédité doit être positif");
        }
        return new Account(id, clientId, soldeCentimes + montantCentimes);
    }

    public Account debiter(long montantCentimes) {
        if (montantCentimes <= 0) {
            throw new IllegalArgumentException("Le montant débité doit être positif");
        }
        if (montantCentimes > soldeCentimes) {
            throw new IllegalStateException("Solde insuffisant pour ce débit");
        }
        return new Account(id, clientId, soldeCentimes - montantCentimes);
    }
}
