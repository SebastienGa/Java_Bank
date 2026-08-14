package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.domain.model.Client;

import java.util.List;

/**
 * Convertit un {@link Client} du domaine et ses données associées en
 * {@link ClientDashboardResponse} destiné à être exposé par l'API.
 */
public final class ClientWebMapper {

    private ClientWebMapper() {
    }

    /**
     * Construit la représentation web du tableau de bord d'un client.
     *
     * @param client  client du domaine
     * @param comptes comptes du client déjà convertis en représentation web
     * @param prets   prêts du client déjà convertis en représentation web
     * @return la représentation web du tableau de bord du client
     */
    public static ClientDashboardResponse toDashboardResponse(Client client, List<AccountResponse> comptes, List<LoanResponse> prets) {
        return new ClientDashboardResponse(client.prenom(), client.nom(), comptes, prets);
    }
}
