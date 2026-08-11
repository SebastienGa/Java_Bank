package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.application.usecase.GetAccountByIdUseCase;
import com.galampoix.bank.application.usecase.ListAccountsUseCase;
import com.galampoix.bank.application.usecase.TransferMoneyUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final ListAccountsUseCase listAccountsUseCase;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final TransferMoneyUseCase transferMoneyUseCase;

    public AccountController(ListAccountsUseCase listAccountsUseCase,
                              GetAccountByIdUseCase getAccountByIdUseCase,
                              TransferMoneyUseCase transferMoneyUseCase) {
        this.listAccountsUseCase = listAccountsUseCase;
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.transferMoneyUseCase = transferMoneyUseCase;
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

    @PostMapping("/{id}/transfer")
    public ResponseEntity<Void> transfer(@PathVariable UUID id, @RequestBody TransferRequest request) {
        transferMoneyUseCase.execute(id, request.destinationAccountId(), request.montantCentimes());
        return ResponseEntity.noContent().build();
    }
}
