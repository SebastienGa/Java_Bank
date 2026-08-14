package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.domain.model.Client;
import com.galampoix.bank.domain.model.Loan;

/**
 * Convertit un prêt du domaine, associé à son client titulaire, en
 * {@link LoanResponse} destiné à l'API REST.
 */
public final class LoanWebMapper {

    private LoanWebMapper() {
    }

    /**
     * Construit la représentation JSON d'un prêt à partir du domaine,
     * en y incluant la progression du remboursement calculée par
     * {@link Loan#calculerProgression()}.
     *
     * @param loan   prêt du domaine à exposer
     * @param client client titulaire du prêt
     * @return la réponse REST correspondante
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
