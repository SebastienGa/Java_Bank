package com.galampoix.bank.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Représente un client de la banque.
 *
 * @param id     identifiant unique du client
 * @param prenom prénom du client (obligatoire, non vide)
 * @param nom    nom du client (obligatoire, non vide)
 * @param email  adresse email du client (obligatoire, non vide)
 */
public record Client(UUID id, String prenom, String nom, String email) {

    /**
     * Valide l'intégrité des données du client à la construction.
     *
     * @throws NullPointerException     si {@code id} est {@code null}
     * @throws IllegalArgumentException si {@code prenom}, {@code nom} ou
     *                                  {@code email} est {@code null} ou vide
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
}
