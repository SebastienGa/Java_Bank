package com.galampoix.bank.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Représente un compte bancaire détenu par un client.
 * <p>
 * Le solde est exprimé en centimes afin d'éviter tout problème de
 * précision lié aux nombres à virgule flottante. Les instances sont
 * immuables : toute opération de crédit ou de débit retourne un nouvel
 * {@link Account} plutôt que de modifier l'instance courante.
 *
 * @param id            identifiant unique du compte
 * @param clientId      identifiant du client titulaire du compte
 * @param soldeCentimes solde courant du compte, en centimes (ne peut pas
 *                      être négatif)
 */
public record Account(UUID id, UUID clientId, long soldeCentimes) {

    /**
     * Valide l'intégrité des données du compte à la construction.
     *
     * @throws NullPointerException     si {@code id} ou {@code clientId} est {@code null}
     * @throws IllegalArgumentException si {@code soldeCentimes} est négatif
     */
    public Account {
        Objects.requireNonNull(id, "L'identifiant du compte est obligatoire");
        Objects.requireNonNull(clientId, "Le client titulaire du compte est obligatoire");
        if (soldeCentimes < 0) {
            throw new IllegalArgumentException("Le solde d'un compte ne peut pas être négatif");
        }
    }

    /**
     * Crée un nouveau compte au solde initial nul.
     *
     * @param id       identifiant unique du compte
     * @param clientId identifiant du client titulaire du compte
     * @return un nouveau compte avec un solde de 0 centime
     */
    public static Account nouveau(UUID id, UUID clientId) {
        return new Account(id, clientId, 0L);
    }

    /**
     * Retourne une copie de ce compte créditée du montant indiqué.
     *
     * @param montantCentimes montant à créditer, en centimes ; doit être
     *                        strictement positif
     * @return un nouveau compte dont le solde a été augmenté du montant indiqué
     * @throws IllegalArgumentException si {@code montantCentimes} n'est pas positif
     */
    public Account crediter(long montantCentimes) {
        if (montantCentimes <= 0) {
            throw new IllegalArgumentException("Le montant crédité doit être positif");
        }
        return new Account(id, clientId, soldeCentimes + montantCentimes);
    }

    /**
     * Retourne une copie de ce compte débitée du montant indiqué.
     *
     * @param montantCentimes montant à débiter, en centimes ; doit être
     *                        strictement positif
     * @return un nouveau compte dont le solde a été diminué du montant indiqué
     * @throws IllegalArgumentException si {@code montantCentimes} n'est pas positif
     * @throws IllegalStateException    si le solde du compte est insuffisant
     *                                  pour couvrir ce débit
     */
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
