package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.domain.model.Loan;

public final class LoanMapper {

    private LoanMapper() {
    }

    public static Loan toDomain(LoanEntity entity) {
        return new Loan(
                entity.getId(),
                entity.getClientId(),
                entity.getMontantInitialCentimes(),
                entity.getMontantRestantCentimes(),
                entity.getTauxInteretPourMille(),
                entity.getMensualiteCentimes(),
                entity.getDateDebut()
        );
    }

    public static LoanEntity toEntity(Loan loan) {
        return new LoanEntity(
                loan.id(),
                loan.clientId(),
                loan.montantInitialCentimes(),
                loan.montantRestantCentimes(),
                loan.tauxInteretPourMille(),
                loan.mensualiteCentimes(),
                loan.dateDebut()
        );
    }
}
