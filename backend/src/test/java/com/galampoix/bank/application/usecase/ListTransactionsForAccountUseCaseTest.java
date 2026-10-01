package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.AccountRepositoryPort;
import com.galampoix.bank.application.port.out.TransactionRepositoryPort;
import com.galampoix.bank.domain.exception.AccountNotFoundException;
import com.galampoix.bank.domain.model.Account;
import com.galampoix.bank.domain.model.Transaction;
import com.galampoix.bank.domain.model.TransactionCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListTransactionsForAccountUseCaseTest {

    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    @Mock
    private TransactionRepositoryPort transactionRepositoryPort;

    private ListTransactionsForAccountUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ListTransactionsForAccountUseCase(accountRepositoryPort, transactionRepositoryPort);
    }

    @Test
    void execute_retourne_les_operations_du_compte() {
        Account compte = new Account(UUID.randomUUID(), UUID.randomUUID(), 1000L);
        Transaction operation = Transaction.credit(compte.id(), UUID.randomUUID(), "Virement de Bob Durand",
                TransactionCategory.VIREMENT, 300L, Instant.now());

        when(accountRepositoryPort.findById(compte.id())).thenReturn(Optional.of(compte));
        when(transactionRepositoryPort.findByAccountId(compte.id())).thenReturn(List.of(operation));

        assertThat(useCase.execute(compte.id())).containsExactly(operation);
    }

    @Test
    void execute_refuse_un_compte_introuvable() {
        UUID accountId = UUID.randomUUID();

        when(accountRepositoryPort.findById(accountId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(accountId))
                .isInstanceOf(AccountNotFoundException.class);

        verify(transactionRepositoryPort, never()).findByAccountId(any());
    }
}
