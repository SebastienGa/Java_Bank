package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.domain.model.TransactionCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "counterpart_account_id")
    private UUID counterpartAccountId;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie", nullable = false, length = 50)
    private TransactionCategory categorie;

    @Column(name = "montant_centimes", nullable = false)
    private long montantCentimes;

    @Column(name = "date_operation", nullable = false)
    private Instant dateOperation;

    protected TransactionEntity() {
        // requis par JPA
    }

    public TransactionEntity(UUID id, UUID accountId, UUID counterpartAccountId, String libelle,
                             TransactionCategory categorie, long montantCentimes, Instant dateOperation) {
        this.id = id;
        this.accountId = accountId;
        this.counterpartAccountId = counterpartAccountId;
        this.libelle = libelle;
        this.categorie = categorie;
        this.montantCentimes = montantCentimes;
        this.dateOperation = dateOperation;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public UUID getCounterpartAccountId() {
        return counterpartAccountId;
    }

    public void setCounterpartAccountId(UUID counterpartAccountId) {
        this.counterpartAccountId = counterpartAccountId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public TransactionCategory getCategorie() {
        return categorie;
    }

    public void setCategorie(TransactionCategory categorie) {
        this.categorie = categorie;
    }

    public long getMontantCentimes() {
        return montantCentimes;
    }

    public void setMontantCentimes(long montantCentimes) {
        this.montantCentimes = montantCentimes;
    }

    public Instant getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Instant dateOperation) {
        this.dateOperation = dateOperation;
    }
}
