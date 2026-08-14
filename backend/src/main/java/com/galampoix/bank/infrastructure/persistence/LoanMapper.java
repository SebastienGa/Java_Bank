package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.domain.model.Loan;

/**
 * Convertit un {@link Loan} du domaine vers/depuis son entité JPA
 * {@link LoanEntity}.
 */
public final class LoanMapper {

    private LoanMapper() {
    }

    /**
     * Convertit une entité JPA en objet de domaine.
     *
     * @param entity entité de prêt persistée
     * @return le prêt du domaine correspondant
     */
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

    /**
     * Convertit un objet de domaine en entité JPA prête à être persistée.
     *
     * @param loan prêt du domaine à convertir
     * @return l'entité JPA correspondante
     */
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
