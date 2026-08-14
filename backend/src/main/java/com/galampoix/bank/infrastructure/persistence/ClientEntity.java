package com.galampoix.bank.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

/**
 * Entité JPA représentant un client dans la table {@code clients}.
 * <p>
 * Sert uniquement d'adaptateur de persistance : la logique métier vit dans
 * le modèle de domaine {@link com.galampoix.bank.domain.model.Client}, la
 * conversion entre les deux étant assurée par {@link ClientMapper}.
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

    /**
     * Constructeur sans argument requis par la spécification JPA.
     */
    protected ClientEntity() {
        // requis par JPA
    }

    /**
     * Crée une entité de client avec l'ensemble de ses champs.
     *
     * @param id     identifiant du client
     * @param prenom prénom du client
     * @param nom    nom de famille du client
     * @param email  adresse email du client
     */
    public ClientEntity(UUID id, String prenom, String nom, String email) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
    }

    /**
     * Retourne l'identifiant du client.
     *
     * @return l'identifiant du client
     */
    public UUID getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du client.
     *
     * @param id nouvel identifiant du client
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Retourne le prénom du client.
     *
     * @return le prénom du client
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Modifie le prénom du client.
     *
     * @param prenom nouveau prénom du client
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Retourne le nom de famille du client.
     *
     * @return le nom du client
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de famille du client.
     *
     * @param nom nouveau nom du client
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne l'adresse email du client.
     *
     * @return l'email du client
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifie l'adresse email du client.
     *
     * @param email nouvel email du client
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
