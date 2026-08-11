package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.application.usecase.GetClientUseCase;
import com.galampoix.bank.application.usecase.ListAccountsForClientUseCase;
import com.galampoix.bank.application.usecase.ListLoansForClientUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final GetClientUseCase getClientUseCase;
    private final ListAccountsForClientUseCase listAccountsForClientUseCase;
    private final ListLoansForClientUseCase listLoansForClientUseCase;

    public ClientController(GetClientUseCase getClientUseCase,
                             ListAccountsForClientUseCase listAccountsForClientUseCase,
                             ListLoansForClientUseCase listLoansForClientUseCase) {
        this.getClientUseCase = getClientUseCase;
        this.listAccountsForClientUseCase = listAccountsForClientUseCase;
        this.listLoansForClientUseCase = listLoansForClientUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDashboardResponse> getClientDashboard(@PathVariable UUID id) {
        return getClientUseCase.execute(id)
                .map(client -> {
                    var comptes = listAccountsForClientUseCase.execute(id).stream()
                            .map(account -> AccountWebMapper.toResponse(account, client))
                            .toList();
                    var prets = listLoansForClientUseCase.execute(id).stream()
                            .map(loan -> LoanWebMapper.toResponse(loan, client))
                            .toList();
                    return ClientWebMapper.toDashboardResponse(client, comptes, prets);
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
