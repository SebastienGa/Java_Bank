package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.AccountRepositoryPort;
import com.galampoix.bank.domain.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListAccountsForClientUseCaseTest {

    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    @Test
    void execute_retourne_les_comptes_du_client() {
        UUID clientId = UUID.randomUUID();
        Account compte = new Account(UUID.randomUUID(), clientId, 1000L);
        when(accountRepositoryPort.findByClientId(clientId)).thenReturn(List.of(compte));

        ListAccountsForClientUseCase useCase = new ListAccountsForClientUseCase(accountRepositoryPort);

        assertThat(useCase.execute(clientId)).containsExactly(compte);
    }
}
