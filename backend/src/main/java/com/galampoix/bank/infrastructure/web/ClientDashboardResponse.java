package com.galampoix.bank.infrastructure.web;

import java.util.List;

/**
 * Représentation web du tableau de bord d'un client, regroupant ses
 * informations personnelles, ses comptes et ses prêts.
 *
 * @param prenom  prénom du client
 * @param nom     nom du client
 * @param comptes liste des comptes détenus par le client
 * @param prets   liste des prêts souscrits par le client
 */
public record ClientDashboardResponse(
        String prenom,
        String nom,
        List<AccountResponse> comptes,
        List<LoanResponse> prets
) {
}
