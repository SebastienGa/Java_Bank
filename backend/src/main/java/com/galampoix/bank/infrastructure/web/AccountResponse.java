package com.galampoix.bank.infrastructure.web;

import java.util.UUID;

/**
 * Représentation JSON d'un compte bancaire exposée par l'API REST,
 * incluant les informations du client titulaire pour éviter un aller-retour
 * supplémentaire côté client.
 *
 * @param id            identifiant du compte
 * @param clientPrenom  prénom du client titulaire
 * @param clientNom     nom du client titulaire
 * @param soldeCentimes solde du compte, en centimes
 */
public record AccountResponse(UUID id, String clientPrenom, String clientNom, long soldeCentimes) {
}
