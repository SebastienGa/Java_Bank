package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.domain.model.Client;
import com.galampoix.bank.domain.model.Loan;

public final class LoanWebMapper {

    private LoanWebMapper() {
    }

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
