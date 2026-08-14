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
 * Entité JPA représentant un compte bancaire dans la table {@code accounts}.
 * <p>
 * Sert uniquement d'adaptateur de persistance : la logique métier vit dans
 * le modèle de domaine {@link com.galampoix.bank.domain.model.Account}, la
 * conversion entre les deux étant assurée par {@link AccountMapper}.
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

    /**
     * Constructeur sans argument requis par la spécification JPA.
     */
    protected AccountEntity() {
        // requis par JPA
    }

    /**
     * Crée une entité de compte avec l'ensemble de ses champs.
     *
     * @param id            identifiant du compte
     * @param client        entité du client titulaire du compte
     * @param soldeCentimes solde du compte, en centimes
     */
    public AccountEntity(UUID id, ClientEntity client, long soldeCentimes) {
        this.id = id;
        this.client = client;
        this.soldeCentimes = soldeCentimes;
    }

    /**
     * Retourne l'identifiant du compte.
     *
     * @return l'identifiant du compte
     */
    public UUID getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du compte.
     *
     * @param id nouvel identifiant du compte
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Retourne l'entité du client titulaire du compte.
     *
     * @return le client titulaire
     */
    public ClientEntity getClient() {
        return client;
    }

    /**
     * Modifie le client titulaire du compte.
     *
     * @param client nouvelle entité client titulaire
     */
    public void setClient(ClientEntity client) {
        this.client = client;
    }

    /**
     * Retourne le solde du compte, en centimes.
     *
     * @return le solde du compte
     */
    public long getSoldeCentimes() {
        return soldeCentimes;
    }

    /**
     * Modifie le solde du compte.
     *
     * @param soldeCentimes nouveau solde du compte, en centimes
     */
    public void setSoldeCentimes(long soldeCentimes) {
        this.soldeCentimes = soldeCentimes;
    }
}
