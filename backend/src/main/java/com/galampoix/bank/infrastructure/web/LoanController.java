package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.application.usecase.ListClientsUseCase;
import com.galampoix.bank.application.usecase.ListLoansUseCase;
import com.galampoix.bank.domain.model.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Contrôleur REST exposant la consultation des prêts, enrichis des
 * informations du client titulaire.
 */
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final ListLoansUseCase listLoansUseCase;
    private final ListClientsUseCase listClientsUseCase;

    public LoanController(ListLoansUseCase listLoansUseCase, ListClientsUseCase listClientsUseCase) {
        this.listLoansUseCase = listLoansUseCase;
        this.listClientsUseCase = listClientsUseCase;
    }

    /**
     * Liste l'ensemble des prêts, enrichis des informations du client titulaire.
     *
     * @return la liste de tous les prêts sous forme de {@link LoanResponse}
     */
    @GetMapping
    public List<LoanResponse> listLoans() {
        Map<UUID, Client> clientsById = listClientsUseCase.execute().stream()
                .collect(Collectors.toMap(Client::id, Function.identity()));
        return listLoansUseCase.execute().stream()
                .map(loan -> LoanWebMapper.toResponse(loan, clientsById.get(loan.clientId())))
                .toList();
    }
}
