package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.domain.model.Loan;

/**
 * Convertit les prêts entre leur représentation métier ({@link Loan}) et
 * leur représentation de persistance JPA ({@link LoanEntity}).
 */
public final class LoanMapper {

    private LoanMapper() {
    }

    /**
     * Convertit une entité JPA en objet de domaine.
     *
     * @param entity entité de prêt issue de la persistance
     * @return le prêt correspondant sous forme d'objet de domaine
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
     * Convertit un objet de domaine en entité JPA.
     *
     * @param loan prêt du domaine à convertir
     * @return l'entité de prêt prête à être persistée
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
