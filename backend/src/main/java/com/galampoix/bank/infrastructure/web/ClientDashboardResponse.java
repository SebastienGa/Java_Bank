package com.galampoix.bank.infrastructure.web;

import java.util.List;

/**
 * Représentation JSON du tableau de bord d'un client, regroupant ses
 * informations personnelles, ses comptes et ses prêts en cours.
 *
 * @param prenom  prénom du client
 * @param nom     nom du client
 * @param comptes liste des comptes détenus par le client
 * @param prets   liste des prêts en cours du client
 */
public record ClientDashboardResponse(
        String prenom,
        String nom,
        List<AccountResponse> comptes,
        List<LoanResponse> prets
) {
}
