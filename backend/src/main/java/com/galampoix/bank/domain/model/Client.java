package com.galampoix.bank.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Client de la banque, titulaire éventuel de comptes et de prêts.
 *
 * @param id      identifiant unique du client
 * @param prenom  prénom du client (non vide)
 * @param nom     nom de famille du client (non vide)
 * @param email   adresse email du client (non vide)
 */
public record Client(UUID id, String prenom, String nom, String email) {

    /**
     * Valide les invariants du client à la construction.
     *
     * @throws NullPointerException     si {@code id} est {@code null}
     * @throws IllegalArgumentException si {@code prenom}, {@code nom} ou {@code email} est {@code null} ou vide
     */
    public Client {
        Objects.requireNonNull(id, "L'identifiant du client est obligatoire");
        if (prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException("Le prénom du client est obligatoire");
        }
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom du client est obligatoire");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("L'email du client est obligatoire");
        }
    }

    /**
     * Retourne le nom complet du client.
     *
     * @return le prénom et le nom du client, séparés par un espace
     */
    public String fullName() {
        return prenom + " " + nom;
    }
}
