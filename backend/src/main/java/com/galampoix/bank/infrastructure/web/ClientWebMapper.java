package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.domain.model.Client;

import java.util.List;

public final class ClientWebMapper {

    private ClientWebMapper() {
    }

    public static ClientDashboardResponse toDashboardResponse(Client client, List<AccountResponse> comptes, List<LoanResponse> prets) {
        return new ClientDashboardResponse(client.prenom(), client.nom(), comptes, prets);
    }
}
