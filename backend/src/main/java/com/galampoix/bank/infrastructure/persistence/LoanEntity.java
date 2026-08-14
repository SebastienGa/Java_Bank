package com.galampoix.bank.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Entité JPA représentant un prêt dans la table {@code loans}.
 * <p>
 * Sert uniquement d'adaptateur de persistance : la logique métier vit dans
 * le modèle de domaine {@link com.galampoix.bank.domain.model.Loan}, la
 * conversion entre les deux étant assurée par {@link LoanMapper}.
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

    /**
     * Constructeur sans argument requis par la spécification JPA.
     */
    protected LoanEntity() {
        // requis par JPA
    }

    /**
     * Crée une entité de prêt avec l'ensemble de ses champs.
     *
     * @param id                     identifiant du prêt
     * @param clientId               identifiant du client titulaire du prêt
     * @param montantInitialCentimes montant initialement emprunté, en centimes
     * @param montantRestantCentimes montant restant à rembourser, en centimes
     * @param tauxInteretPourMille   taux d'intérêt du prêt, en pour-mille
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
     * Retourne l'identifiant du prêt.
     *
     * @return l'identifiant du prêt
     */
    public UUID getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du prêt.
     *
     * @param id nouvel identifiant du prêt
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Retourne l'identifiant du client titulaire du prêt.
     *
     * @return l'identifiant du client titulaire
     */
    public UUID getClientId() {
        return clientId;
    }

    /**
     * Modifie l'identifiant du client titulaire du prêt.
     *
     * @param clientId nouvel identifiant du client titulaire
     */
    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    /**
     * Retourne le montant initialement emprunté, en centimes.
     *
     * @return le montant initial du prêt
     */
    public long getMontantInitialCentimes() {
        return montantInitialCentimes;
    }

    /**
     * Modifie le montant initialement emprunté.
     *
     * @param montantInitialCentimes nouveau montant initial, en centimes
     */
    public void setMontantInitialCentimes(long montantInitialCentimes) {
        this.montantInitialCentimes = montantInitialCentimes;
    }

    /**
     * Retourne le montant restant à rembourser, en centimes.
     *
     * @return le montant restant à rembourser
     */
    public long getMontantRestantCentimes() {
        return montantRestantCentimes;
    }

    /**
     * Modifie le montant restant à rembourser.
     *
     * @param montantRestantCentimes nouveau montant restant, en centimes
     */
    public void setMontantRestantCentimes(long montantRestantCentimes) {
        this.montantRestantCentimes = montantRestantCentimes;
    }

    /**
     * Retourne le taux d'intérêt du prêt, en pour-mille.
     *
     * @return le taux d'intérêt du prêt
     */
    public int getTauxInteretPourMille() {
        return tauxInteretPourMille;
    }

    /**
     * Modifie le taux d'intérêt du prêt.
     *
     * @param tauxInteretPourMille nouveau taux d'intérêt, en pour-mille
     */
    public void setTauxInteretPourMille(int tauxInteretPourMille) {
        this.tauxInteretPourMille = tauxInteretPourMille;
    }

    /**
     * Retourne le montant de la mensualité de remboursement, en centimes.
     *
     * @return le montant de la mensualité
     */
    public long getMensualiteCentimes() {
        return mensualiteCentimes;
    }

    /**
     * Modifie le montant de la mensualité de remboursement.
     *
     * @param mensualiteCentimes nouveau montant de la mensualité, en centimes
     */
    public void setMensualiteCentimes(long mensualiteCentimes) {
        this.mensualiteCentimes = mensualiteCentimes;
    }

    /**
     * Retourne la date de début du prêt.
     *
     * @return la date de début du prêt
     */
    public LocalDate getDateDebut() {
        return dateDebut;
    }

    /**
     * Modifie la date de début du prêt.
     *
     * @param dateDebut nouvelle date de début du prêt
     */
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
}
