package com.galampoix.bank.infrastructure.web;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Représentation JSON d'un prêt exposée par l'API REST, incluant les
 * informations du client titulaire ainsi que la progression du
 * remboursement.
 *
 * @param id                      identifiant du prêt
 * @param clientPrenom            prénom du client titulaire
 * @param clientNom               nom du client titulaire
 * @param montantInitialCentimes  montant initialement emprunté, en centimes
 * @param montantRestantCentimes  montant restant à rembourser, en centimes
 * @param tauxInteretPourMille    taux d'intérêt du prêt, en pour-mille
 * @param mensualiteCentimes      montant de la mensualité, en centimes
 * @param dateDebut               date de début du prêt
 * @param progression             pourcentage du prêt déjà remboursé, entre 0 et 100
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
