package com.galampoix.bank.infrastructure.persistence;

import com.galampoix.bank.application.port.out.ClientRepositoryPort;
import com.galampoix.bank.domain.model.Client;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaClientRepositoryAdapter implements ClientRepositoryPort {

    private final SpringDataClientRepository springDataClientRepository;

    public JpaClientRepositoryAdapter(SpringDataClientRepository springDataClientRepository) {
        this.springDataClientRepository = springDataClientRepository;
    }

    @Override
    public Optional<Client> findById(UUID id) {
        return springDataClientRepository.findById(id)
                .map(ClientMapper::toDomain);
    }

    @Override
    public List<Client> findAll() {
        return springDataClientRepository.findAll().stream()
                .map(ClientMapper::toDomain)
                .toList();
    }
}
