package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.domain.model.Account;
import com.galampoix.bank.domain.model.Client;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AccountWebMapperTest {

    @Test
    void toResponse_expose_le_client_titulaire_et_son_identifiant() {
        UUID clientId = UUID.randomUUID();
        Account compte = new Account(UUID.randomUUID(), clientId, 131500L);
        Client client = new Client(clientId, "Alice", "Martin", "alice@example.com");

        AccountResponse response = AccountWebMapper.toResponse(compte, client);

        assertThat(response).isEqualTo(new AccountResponse(compte.id(), clientId, "Alice", "Martin", 131500L));
    }
}
