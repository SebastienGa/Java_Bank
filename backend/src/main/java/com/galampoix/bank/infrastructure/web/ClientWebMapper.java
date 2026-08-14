package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.domain.model.Client;

import java.util.List;

/**
 * Convertit un client du domaine, associé à ses comptes et prêts déjà
 * transformés en réponses REST, en {@link ClientDashboardResponse}.
 */
public final class ClientWebMapper {

    private ClientWebMapper() {
    }

    /**
     * Construit la représentation JSON du tableau de bord d'un client.
     *
     * @param client  client du domaine à exposer
     * @param comptes comptes du client déjà convertis en {@link AccountResponse}
     * @param prets   prêts du client déjà convertis en {@link LoanResponse}
     * @return la réponse REST correspondant au tableau de bord du client
     */
    public static ClientDashboardResponse toDashboardResponse(Client client, List<AccountResponse> comptes, List<LoanResponse> prets) {
        return new ClientDashboardResponse(client.prenom(), client.nom(), comptes, prets);
    }
}
