package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.ClientRepositoryPort;
import com.galampoix.bank.domain.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Cas d'utilisation : lister l'ensemble des clients.
 * <p>
 * Ne dépend que du port de sortie {@link ClientRepositoryPort}, jamais
 * d'une implémentation concrète (JPA, etc.).
 */
@Service
public class ListClientsUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    public ListClientsUseCase(ClientRepositoryPort clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    /**
     * Récupère l'ensemble des clients existants.
     *
     * @return la liste de tous les clients
     */
    public List<Client> execute() {
        return clientRepositoryPort.findAll();
    }
}
