package com.galampoix.bank.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public record Loan(
        UUID id,
        UUID clientId,
        long montantInitialCentimes,
        long montantRestantCentimes,
        int tauxInteretPourMille,
        long mensualiteCentimes,
        LocalDate dateDebut
) {

    public Loan {
        Objects.requireNonNull(id, "L'identifiant du prêt est obligatoire");
        Objects.requireNonNull(clientId, "Le client titulaire du prêt est obligatoire");
        Objects.requireNonNull(dateDebut, "La date de début du prêt est obligatoire");
        if (montantInitialCentimes <= 0) {
            throw new IllegalArgumentException("Le montant initial du prêt doit être positif");
        }
        if (montantRestantCentimes < 0 || montantRestantCentimes > montantInitialCentimes) {
            throw new IllegalArgumentException("Le montant restant doit être compris entre 0 et le montant initial");
        }
        if (tauxInteretPourMille < 0) {
            throw new IllegalArgumentException("Le taux d'intérêt ne peut pas être négatif");
        }
        if (mensualiteCentimes <= 0) {
            throw new IllegalArgumentException("La mensualité doit être positive");
        }
    }

    public int calculerProgression() {
        long montantRembourse = montantInitialCentimes - montantRestantCentimes;
        return (int) Math.round((montantRembourse * 100.0) / montantInitialCentimes);
    }
}
