package com.galampoix.bank.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Compte bancaire appartenant à un client.
 * <p>
 * Il s'agit d'un objet de valeur immuable : toute opération (crédit, débit)
 * ne modifie pas l'instance courante mais retourne une nouvelle instance
 * représentant le compte après l'opération. Le solde est exprimé en
 * centimes afin d'éviter les problèmes d'arrondi liés aux nombres à
 * virgule flottante.
 *
 * @param id             identifiant unique du compte
 * @param clientId       identifiant du client titulaire du compte
 * @param soldeCentimes  solde courant du compte, en centimes (toujours positif ou nul)
 */
public record Account(UUID id, UUID clientId, long soldeCentimes) {

    /**
     * Valide les invariants du compte à la construction.
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
     * Crée un nouveau compte vide (solde à zéro) pour un client donné.
     *
     * @param id       identifiant à attribuer au nouveau compte
     * @param clientId identifiant du client titulaire
     * @return un nouveau compte avec un solde de 0 centime
     */
    public static Account nouveau(UUID id, UUID clientId) {
        return new Account(id, clientId, 0L);
    }

    /**
     * Crédite le compte du montant indiqué.
     *
     * @param montantCentimes montant à créditer, en centimes (doit être strictement positif)
     * @return une nouvelle instance du compte avec le solde augmenté du montant crédité
     * @throws IllegalArgumentException si {@code montantCentimes} n'est pas strictement positif
     */
    public Account crediter(long montantCentimes) {
        if (montantCentimes <= 0) {
            throw new IllegalArgumentException("Le montant crédité doit être positif");
        }
        return new Account(id, clientId, soldeCentimes + montantCentimes);
    }

    /**
     * Débite le compte du montant indiqué.
     *
     * @param montantCentimes montant à débiter, en centimes (doit être strictement positif)
     * @return une nouvelle instance du compte avec le solde diminué du montant débité
     * @throws IllegalArgumentException si {@code montantCentimes} n'est pas strictement positif
     * @throws IllegalStateException    si le solde du compte est insuffisant pour couvrir le débit
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

    /**
     * Indique si le compte est actif.
     * <p>
     * Un compte est considéré comme actif tant que son solde reste positif
     * ou nul. Les invariants du record garantissant déjà qu'un
     * {@code Account} ne peut jamais être construit avec un solde négatif,
     * cette méthode retourne actuellement toujours {@code true} ; elle sert
     * de point d'extension explicite pour une future notion de statut de
     * compte (par exemple un compte clôturé ou suspendu).
     *
     * @return {@code true} si le solde du compte est positif ou nul, {@code false} sinon
     */
    public boolean estActif() {
        return soldeCentimes >= 0;
    }
}
