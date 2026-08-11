package com.galampoix.bank.application.port.out;

import com.galampoix.bank.domain.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepositoryPort {

    Optional<Client> findById(UUID id);

    List<Client> findAll();
}
