package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.application.usecase.ListRecentTransactionsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final ListRecentTransactionsUseCase listRecentTransactionsUseCase;

    public TransactionController(ListRecentTransactionsUseCase listRecentTransactionsUseCase) {
        this.listRecentTransactionsUseCase = listRecentTransactionsUseCase;
    }

    @GetMapping
    public List<TransactionResponse> listRecentTransactions(@RequestParam(defaultValue = "20") int limit) {
        return listRecentTransactionsUseCase.execute(limit).stream()
                .map(TransactionWebMapper::toResponse)
                .toList();
    }
}
