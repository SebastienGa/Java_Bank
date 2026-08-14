package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.domain.model.Client;
import com.galampoix.bank.domain.model.Loan;

/**
 * Convertit un {@link Loan} du domaine en {@link LoanResponse} destiné à
 * être exposé par l'API.
 */
public final class LoanWebMapper {

    private LoanWebMapper() {
    }

    /**
     * Construit la représentation web d'un prêt, enrichie des informations
     * du client titulaire et de la progression du remboursement.
     *
     * @param loan   prêt du domaine à convertir
     * @param client client titulaire du prêt
     * @return la représentation web du prêt
     */
    public static LoanResponse toResponse(Loan loan, Client client) {
        return new LoanResponse(
                loan.id(),
                client.prenom(),
                client.nom(),
                loan.montantInitialCentimes(),
                loan.montantRestantCentimes(),
                loan.tauxInteretPourMille(),
                loan.mensualiteCentimes(),
                loan.dateDebut(),
                loan.calculerProgression()
        );
    }
}
