package com.galampoix.bank.infrastructure.web;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Représentation web d'un prêt, enrichie du nom et prénom du client
 * titulaire ainsi que de la progression du remboursement.
 *
 * @param id                     identifiant unique du prêt
 * @param clientPrenom           prénom du client titulaire du prêt
 * @param clientNom              nom du client titulaire du prêt
 * @param montantInitialCentimes montant initial emprunté, en centimes
 * @param montantRestantCentimes montant restant dû, en centimes
 * @param tauxInteretPourMille   taux d'intérêt du prêt, en pour mille
 * @param mensualiteCentimes     montant de la mensualité, en centimes
 * @param dateDebut              date de début du prêt
 * @param progression            pourcentage du prêt déjà remboursé (0 à 100)
 */
public record LoanResponse(
        UUID id,
        String clientPrenom,
        String clientNom,
        long montantInitialCentimes,
        long montantRestantCentimes,
        int tauxInteretPourMille,
        long mensualiteCentimes,
        LocalDate dateDebut,
        int progression
) {
}
