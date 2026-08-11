package com.galampoix.bank.domain.model;

import java.util.Objects;
import java.util.UUID;

public record Client(UUID id, String prenom, String nom, String email) {

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
