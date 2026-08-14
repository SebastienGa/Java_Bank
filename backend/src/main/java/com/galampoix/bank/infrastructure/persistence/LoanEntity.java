package com.galampoix.bank.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Entité JPA représentant la table {@code loans}.
 * <p>
 * Sert uniquement à la persistance ; la conversion vers/depuis le modèle
 * de domaine {@link com.galampoix.bank.domain.model.Loan} est réalisée
 * par {@link LoanMapper}.
 */
@Entity
@Table(name = "loans")
public class LoanEntity {

    @Id
    private UUID id;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "montant_initial_centimes", nullable = false)
    private long montantInitialCentimes;

    @Column(name = "montant_restant_centimes", nullable = false)
    private long montantRestantCentimes;

    @Column(name = "taux_interet_pour_mille", nullable = false)
    private int tauxInteretPourMille;

    @Column(name = "mensualite_centimes", nullable = false)
    private long mensualiteCentimes;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    protected LoanEntity() {
        // requis par JPA
    }

    /**
     * Crée une entité de prêt.
     *
     * @param id                     identifiant unique du prêt
     * @param clientId               identifiant du client titulaire du prêt
     * @param montantInitialCentimes montant initial emprunté, en centimes
     * @param montantRestantCentimes montant restant dû, en centimes
     * @param tauxInteretPourMille   taux d'intérêt du prêt, en pour mille
     * @param mensualiteCentimes     montant de la mensualité, en centimes
     * @param dateDebut              date de début du prêt
     */
    public LoanEntity(UUID id, UUID clientId, long montantInitialCentimes, long montantRestantCentimes,
                       int tauxInteretPourMille, long mensualiteCentimes, LocalDate dateDebut) {
        this.id = id;
        this.clientId = clientId;
        this.montantInitialCentimes = montantInitialCentimes;
        this.montantRestantCentimes = montantRestantCentimes;
        this.tauxInteretPourMille = tauxInteretPourMille;
        this.mensualiteCentimes = mensualiteCentimes;
        this.dateDebut = dateDebut;
    }

    /**
     * @return identifiant unique du prêt
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id identifiant unique du prêt
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return identifiant du client titulaire du prêt
     */
    public UUID getClientId() {
        return clientId;
    }

    /**
     * @param clientId identifiant du client titulaire du prêt
     */
    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    /**
     * @return montant initial emprunté, en centimes
     */
    public long getMontantInitialCentimes() {
        return montantInitialCentimes;
    }

    /**
     * @param montantInitialCentimes montant initial emprunté, en centimes
     */
    public void setMontantInitialCentimes(long montantInitialCentimes) {
        this.montantInitialCentimes = montantInitialCentimes;
    }

    /**
     * @return montant restant dû, en centimes
     */
    public long getMontantRestantCentimes() {
        return montantRestantCentimes;
    }

    /**
     * @param montantRestantCentimes montant restant dû, en centimes
     */
    public void setMontantRestantCentimes(long montantRestantCentimes) {
        this.montantRestantCentimes = montantRestantCentimes;
    }

    /**
     * @return taux d'intérêt du prêt, en pour mille
     */
    public int getTauxInteretPourMille() {
        return tauxInteretPourMille;
    }

    /**
     * @param tauxInteretPourMille taux d'intérêt du prêt, en pour mille
     */
    public void setTauxInteretPourMille(int tauxInteretPourMille) {
        this.tauxInteretPourMille = tauxInteretPourMille;
    }

    /**
     * @return montant de la mensualité, en centimes
     */
    public long getMensualiteCentimes() {
        return mensualiteCentimes;
    }

    /**
     * @param mensualiteCentimes montant de la mensualité, en centimes
     */
    public void setMensualiteCentimes(long mensualiteCentimes) {
        this.mensualiteCentimes = mensualiteCentimes;
    }

    /**
     * @return date de début du prêt
     */
    public LocalDate getDateDebut() {
        return dateDebut;
    }

    /**
     * @param dateDebut date de début du prêt
     */
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
}
