package com.galampoix.bank.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Entité métier représentant un compte bancaire.
 * <p>
 * Aucune dépendance vers Spring, JPA ou tout autre framework : cette classe
 * ne connaît que les règles métier du domaine bancaire. Le solde est exprimé
 * en centimes (long) pour éviter les problèmes d'arrondi liés aux nombres
 * flottants.
 * <p>
 * Immutable par construction : toute opération qui modifie le solde
 * (crédit/débit) retourne une nouvelle instance plutôt que de muter l'état
 * existant.
 */
public record Account(UUID id, String titulaire, long soldeCentimes) {

    public Account {
        Objects.requireNonNull(id, "L'identifiant du compte est obligatoire");
        if (titulaire == null || titulaire.isBlank()) {
            throw new IllegalArgumentException("Le titulaire du compte est obligatoire");
        }
        if (soldeCentimes < 0) {
            throw new IllegalArgumentException("Le solde d'un compte ne peut pas être négatif");
        }
    }

    /**
     * Crée un nouveau compte avec un solde initial nul.
     */
    public static Account nouveau(UUID id, String titulaire) {
        return new Account(id, titulaire, 0L);
    }

    /**
     * Crédite le compte du montant donné et retourne le nouveau compte résultant.
     */
    public Account crediter(long montantCentimes) {
        if (montantCentimes <= 0) {
            throw new IllegalArgumentException("Le montant crédité doit être positif");
        }
        return new Account(id, titulaire, soldeCentimes + montantCentimes);
    }

    /**
     * Débite le compte du montant donné et retourne le nouveau compte résultant.
     * Refuse l'opération si elle conduirait à un solde négatif.
     */
    public Account debiter(long montantCentimes) {
        if (montantCentimes <= 0) {
            throw new IllegalArgumentException("Le montant débité doit être positif");
        }
        if (montantCentimes > soldeCentimes) {
            throw new IllegalStateException("Solde insuffisant pour ce débit");
        }
        return new Account(id, titulaire, soldeCentimes - montantCentimes);
    }
}
