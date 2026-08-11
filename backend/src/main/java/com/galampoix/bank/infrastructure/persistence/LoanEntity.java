package com.galampoix.bank.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public long getMontantInitialCentimes() {
        return montantInitialCentimes;
    }

    public void setMontantInitialCentimes(long montantInitialCentimes) {
        this.montantInitialCentimes = montantInitialCentimes;
    }

    public long getMontantRestantCentimes() {
        return montantRestantCentimes;
    }

    public void setMontantRestantCentimes(long montantRestantCentimes) {
        this.montantRestantCentimes = montantRestantCentimes;
    }

    public int getTauxInteretPourMille() {
        return tauxInteretPourMille;
    }

    public void setTauxInteretPourMille(int tauxInteretPourMille) {
        this.tauxInteretPourMille = tauxInteretPourMille;
    }

    public long getMensualiteCentimes() {
        return mensualiteCentimes;
    }

    public void setMensualiteCentimes(long mensualiteCentimes) {
        this.mensualiteCentimes = mensualiteCentimes;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
}
