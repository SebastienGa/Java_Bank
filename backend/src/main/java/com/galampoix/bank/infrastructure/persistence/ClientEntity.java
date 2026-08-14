package com.galampoix.bank.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

/**
 * Entité JPA représentant la table {@code clients}.
 * <p>
 * Sert uniquement à la persistance ; la conversion vers/depuis le modèle
 * de domaine {@link com.galampoix.bank.domain.model.Client} est réalisée
 * par {@link ClientMapper}.
 */
@Entity
@Table(name = "clients")
public class ClientEntity {

    @Id
    private UUID id;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "email", nullable = false)
    private String email;

    protected ClientEntity() {
        // requis par JPA
    }

    /**
     * Crée une entité de client.
     *
     * @param id     identifiant unique du client
     * @param prenom prénom du client
     * @param nom    nom du client
     * @param email  adresse email du client
     */
    public ClientEntity(UUID id, String prenom, String nom, String email) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
    }

    /**
     * @return identifiant unique du client
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id identifiant unique du client
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return prénom du client
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom prénom du client
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return nom du client
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom nom du client
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return adresse email du client
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email adresse email du client
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
