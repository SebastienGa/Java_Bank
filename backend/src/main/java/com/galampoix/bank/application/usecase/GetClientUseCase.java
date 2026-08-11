package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.ClientRepositoryPort;
import com.galampoix.bank.domain.model.Client;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    public GetClientUseCase(ClientRepositoryPort clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    public Optional<Client> execute(UUID id) {
        return clientRepositoryPort.findById(id);
    }
}
