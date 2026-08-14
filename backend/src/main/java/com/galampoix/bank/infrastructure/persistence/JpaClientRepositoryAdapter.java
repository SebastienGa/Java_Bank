package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.application.port.out.ClientRepositoryPort;
import com.galampoix.bank.domain.model.Client;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implémentation JPA du port de sortie {@link ClientRepositoryPort}.
 * <p>
 * Délègue l'accès aux données à {@link SpringDataClientRepository} et
 * convertit les entités JPA en objets de domaine via {@link ClientMapper}.
 */
@Repository
public class JpaClientRepositoryAdapter implements ClientRepositoryPort {

    private final SpringDataClientRepository springDataClientRepository;

    public JpaClientRepositoryAdapter(SpringDataClientRepository springDataClientRepository) {
        this.springDataClientRepository = springDataClientRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Client> findById(UUID id) {
        return springDataClientRepository.findById(id)
                .map(ClientMapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findAll() {
        return springDataClientRepository.findAll().stream()
                .map(ClientMapper::toDomain)
                .toList();
    }
}
