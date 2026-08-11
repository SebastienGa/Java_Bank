package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.application.usecase.GetAccountByIdUseCase;
import com.galampoix.bank.application.usecase.ListAccountsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Adaptateur d'entrée REST. Ne contient aucune logique métier : il délègue
 * aux cas d'utilisation de la couche application et se contente de
 * traduire domaine -> DTO via {@link AccountWebMapper}.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final ListAccountsUseCase listAccountsUseCase;
    private final GetAccountByIdUseCase getAccountByIdUseCase;

    public AccountController(ListAccountsUseCase listAccountsUseCase,
                              GetAccountByIdUseCase getAccountByIdUseCase) {
        this.listAccountsUseCase = listAccountsUseCase;
        this.getAccountByIdUseCase = getAccountByIdUseCase;
    }

    @GetMapping
    public List<AccountResponse> listAccounts() {
        return listAccountsUseCase.execute().stream()
                .map(AccountWebMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable UUID id) {
        return getAccountByIdUseCase.execute(id)
                .map(AccountWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
