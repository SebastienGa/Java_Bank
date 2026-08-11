package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.ClientRepositoryPort;
import com.galampoix.bank.domain.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetClientUseCaseTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @Test
    void execute_retourne_le_client_correspondant_a_l_id() {
        Client client = new Client(UUID.randomUUID(), "Alice", "Martin", "alice.martin@example.com");
        when(clientRepositoryPort.findById(client.id())).thenReturn(Optional.of(client));

        GetClientUseCase useCase = new GetClientUseCase(clientRepositoryPort);

        assertThat(useCase.execute(client.id())).contains(client);
    }

    @Test
    void execute_retourne_empty_si_le_client_est_introuvable() {
        UUID id = UUID.randomUUID();
        when(clientRepositoryPort.findById(id)).thenReturn(Optional.empty());

        GetClientUseCase useCase = new GetClientUseCase(clientRepositoryPort);

        assertThat(useCase.execute(id)).isEmpty();
    }
}
