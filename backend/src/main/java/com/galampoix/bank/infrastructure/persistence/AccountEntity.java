package com.galampoix.bank.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

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

    public AccountEntity(UUID id, ClientEntity client, long soldeCentimes) {
        this.id = id;
        this.client = client;
        this.soldeCentimes = soldeCentimes;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public long getSoldeCentimes() {
        return soldeCentimes;
    }

    public void setSoldeCentimes(long soldeCentimes) {
        this.soldeCentimes = soldeCentimes;
    }
}
