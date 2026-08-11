package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.ClientRepositoryPort;
import com.galampoix.bank.domain.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListClientsUseCaseTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @Test
    void execute_retourne_tous_les_clients() {
        Client alice = new Client(UUID.randomUUID(), "Alice", "Martin", "alice.martin@example.com");
        Client bob = new Client(UUID.randomUUID(), "Bob", "Durand", "bob.durand@example.com");
        when(clientRepositoryPort.findAll()).thenReturn(List.of(alice, bob));

        ListClientsUseCase useCase = new ListClientsUseCase(clientRepositoryPort);

        assertThat(useCase.execute()).containsExactly(alice, bob);
    }
}
