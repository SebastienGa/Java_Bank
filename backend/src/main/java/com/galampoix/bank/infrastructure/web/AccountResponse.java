package com.galampoix.bank.infrastructure.web;

import java.util.UUID;

public record AccountResponse(UUID id, String clientPrenom, String clientNom, long soldeCentimes) {
}
