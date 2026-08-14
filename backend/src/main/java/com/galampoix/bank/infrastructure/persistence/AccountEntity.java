package com.galampoix.bank.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

/**
 * Entité JPA représentant la table {@code accounts}.
 * <p>
 * Sert uniquement à la persistance ; la conversion vers/depuis le modèle
 * de domaine {@link com.galampoix.bank.domain.model.Account} est réalisée
 * par {@link AccountMapper}.
 */
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @Column(name = "solde_centimes", nullable = false)
    private long soldeCentimes;

    protected AccountEntity() {
        // requis par JPA
    }

    /**
     * Crée une entité de compte.
     *
     * @param id            identifiant unique du compte
     * @param client        entité du client titulaire du compte
     * @param soldeCentimes solde du compte, en centimes
     */
    public AccountEntity(UUID id, ClientEntity client, long soldeCentimes) {
        this.id = id;
        this.client = client;
        this.soldeCentimes = soldeCentimes;
    }

    /**
     * @return identifiant unique du compte
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id identifiant unique du compte
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return entité du client titulaire du compte
     */
    public ClientEntity getClient() {
        return client;
    }

    /**
     * @param client entité du client titulaire du compte
     */
    public void setClient(ClientEntity client) {
        this.client = client;
    }

    /**
     * @return solde du compte, en centimes
     */
    public long getSoldeCentimes() {
        return soldeCentimes;
    }

    /**
     * @param soldeCentimes solde du compte, en centimes
     */
    public void setSoldeCentimes(long soldeCentimes) {
        this.soldeCentimes = soldeCentimes;
    }
}
