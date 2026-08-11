package com.galampoix.bank.infrastructure.web;

import java.util.UUID;

/**
 * DTO de réponse exposé par l'API REST.
 * <p>
 * Distinct de l'entité de domaine {@code Account} : c'est le contrat
 * public de l'API, qui peut évoluer indépendamment du modèle métier.
 */
public record AccountResponse(UUID id, String titulaire, long soldeCentimes) {
}
