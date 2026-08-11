package com.galampoix.bank.infrastructure.web;

import java.util.List;

public record ClientDashboardResponse(
        String prenom,
        String nom,
        List<AccountResponse> comptes,
        List<LoanResponse> prets
) {
}
