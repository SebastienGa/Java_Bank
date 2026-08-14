package com.galampoix.bank.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Représente un prêt souscrit par un client.
 * <p>
 * Les montants sont exprimés en centimes afin d'éviter tout problème de
 * précision lié aux nombres à virgule flottante.
 *
 * @param id                     identifiant unique du prêt
 * @param clientId               identifiant du client titulaire du prêt
 * @param montantInitialCentimes montant initial emprunté, en centimes
 *                               (doit être strictement positif)
 * @param montantRestantCentimes montant restant dû, en centimes (doit être
 *                               compris entre 0 et {@code montantInitialCentimes})
 * @param tauxInteretPourMille   taux d'intérêt du prêt, exprimé en pour
 *                               mille (doit être positif ou nul)
 * @param mensualiteCentimes     montant de la mensualité, en centimes
 *                               (doit être strictement positif)
 * @param dateDebut              date de début du prêt
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
     * Valide l'intégrité des données du prêt à la construction.
     *
     * @throws NullPointerException     si {@code id}, {@code clientId} ou
     *                                  {@code dateDebut} est {@code null}
     * @throws IllegalArgumentException si l'un des montants ou taux est
     *                                  incohérent (voir description des
     *                                  paramètres)
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
     * Calcule le pourcentage de remboursement déjà effectué sur ce prêt.
     *
     * @return la progression du remboursement, en pourcentage entier arrondi
     *         (0 à 100)
     */
    public int calculerProgression() {
        long montantRembourse = montantInitialCentimes - montantRestantCentimes;
        return (int) Math.round((montantRembourse * 100.0) / montantInitialCentimes);
    }
}
