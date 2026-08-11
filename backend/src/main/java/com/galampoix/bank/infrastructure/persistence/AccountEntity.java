package com.galampoix.bank.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

/**
 * Entité JPA persistée en base. Distincte du modèle de domaine
 * {@code com.galampoix.bank.domain.model.Account} : celle-ci porte les
 * préoccupations techniques (mapping table/colonnes), le domaine porte les
 * règles métier.
 * <p>
 * Constructeur sans argument et setters requis par JPA/Hibernate.
 */
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    private UUID id;

    @Column(name = "titulaire", nullable = false)
    private String titulaire;

    @Column(name = "solde_centimes", nullable = false)
    private long soldeCentimes;

    protected AccountEntity() {
        // requis par JPA
    }

    public AccountEntity(UUID id, String titulaire, long soldeCentimes) {
        this.id = id;
        this.titulaire = titulaire;
        this.soldeCentimes = soldeCentimes;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public long getSoldeCentimes() {
        return soldeCentimes;
    }

    public void setSoldeCentimes(long soldeCentimes) {
        this.soldeCentimes = soldeCentimes;
    }
}
