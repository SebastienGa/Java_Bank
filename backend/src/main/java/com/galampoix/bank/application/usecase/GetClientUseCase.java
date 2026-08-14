package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.ClientRepositoryPort;
import com.galampoix.bank.domain.model.Client;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Cas d'utilisation : récupérer un client par son identifiant.
 * <p>
 * Ne dépend que du port de sortie {@link ClientRepositoryPort}, jamais
 * d'une implémentation concrète (JPA, etc.).
 */
@Service
public class GetClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    public GetClientUseCase(ClientRepositoryPort clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    /**
     * Recherche un client par son identifiant.
     *
     * @param id identifiant du client recherché
     * @return le client correspondant, ou {@link Optional#empty()} si aucun
     *         client ne possède cet identifiant
     */
    public Optional<Client> execute(UUID id) {
        return clientRepositoryPort.findById(id);
    }
}
