package com.galampoix.bank.infrastructure.web;

import java.util.UUID;

public record TransferRequest(UUID destinationAccountId, long montantCentimes) {
}
