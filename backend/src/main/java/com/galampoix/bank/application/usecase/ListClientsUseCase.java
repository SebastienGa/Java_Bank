package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.ClientRepositoryPort;
import com.galampoix.bank.domain.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListClientsUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    public ListClientsUseCase(ClientRepositoryPort clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    public List<Client> execute() {
        return clientRepositoryPort.findAll();
    }
}
