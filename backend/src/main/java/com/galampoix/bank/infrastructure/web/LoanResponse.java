package com.galampoix.bank.infrastructure.web;

import java.time.LocalDate;
import java.util.UUID;

public record LoanResponse(
        UUID id,
        String clientPrenom,
        String clientNom,
        long montantInitialCentimes,
        long montantRestantCentimes,
        int tauxInteretPourMille,
        long mensualiteCentimes,
        LocalDate dateDebut,
        int progression
) {
}
