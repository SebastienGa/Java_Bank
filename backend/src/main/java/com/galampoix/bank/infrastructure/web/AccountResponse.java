package com.galampoix.bank.infrastructure.web;

import java.util.UUID;

/**
 * Représentation web d'un compte bancaire, enrichie du nom et prénom du
 * client titulaire pour éviter un appel supplémentaire côté client de l'API.
 *
 * @param id             identifiant unique du compte
 * @param clientPrenom   prénom du client titulaire du compte
 * @param clientNom      nom du client titulaire du compte
 * @param soldeCentimes  solde courant du compte, en centimes
 */
public record AccountResponse(UUID id, String clientPrenom, String clientNom, long soldeCentimes) {
}
