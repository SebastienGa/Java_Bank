package com.galampoix.bank.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Prêt accordé à un client.
 * <p>
 * Tous les montants sont exprimés en centimes afin d'éviter les problèmes
 * d'arrondi liés aux nombres à virgule flottante.
 *
 * @param id                       identifiant unique du prêt
 * @param clientId                 identifiant du client titulaire du prêt
 * @param montantInitialCentimes   montant initialement emprunté, en centimes (strictement positif)
 * @param montantRestantCentimes   montant restant à rembourser, en centimes (compris entre 0 et le montant initial)
 * @param tauxInteretPourMille     taux d'intérêt du prêt, exprimé en pour-mille (jamais négatif)
 * @param mensualiteCentimes       montant de la mensualité de remboursement, en centimes (strictement positif)
 * @param dateDebut                date de début du prêt
 */
public record Loan(
        UUID id,
        UUID clientId,
        long montantInitialCentimes,
        long montantRestantCentimes,
        int tauxInteretPourMille,
        long mensualiteCentimes,
        LocalDate dateDebut
) {

    /**
     * Valide les invariants du prêt à la construction.
     *
     * @throws NullPointerException     si {@code id}, {@code clientId} ou {@code dateDebut} est {@code null}
     * @throws IllegalArgumentException si les montants, le taux ou la mensualité sont incohérents
     */
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

    /**
     * Calcule le pourcentage du prêt déjà remboursé.
     *
     * @return la progression du remboursement, arrondie à l'entier le plus proche, entre 0 et 100
     */
    public int calculerProgression() {
        long montantRembourse = montantInitialCentimes - montantRestantCentimes;
        return (int) Math.round((montantRembourse * 100.0) / montantInitialCentimes);
    }
}
