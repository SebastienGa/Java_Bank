package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.TransactionRepositoryPort;
import com.galampoix.bank.domain.model.Transaction;
import com.galampoix.bank.domain.model.TransactionCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListRecentTransactionsUseCaseTest {

    @Mock
    private TransactionRepositoryPort transactionRepositoryPort;

    private ListRecentTransactionsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ListRecentTransactionsUseCase(transactionRepositoryPort);
    }

    @Test
    void execute_retourne_les_operations_recentes() {
        Transaction operation = Transaction.debit(UUID.randomUUID(), UUID.randomUUID(), "Virement vers Bob Durand",
                TransactionCategory.VIREMENT, 300L, Instant.now());

        when(transactionRepositoryPort.findRecent(20)).thenReturn(List.of(operation));

        assertThat(useCase.execute(20)).containsExactly(operation);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 101})
    void execute_refuse_une_limite_hors_bornes(int limit) {
        assertThatThrownBy(() -> useCase.execute(limit))
                .isInstanceOf(IllegalArgumentException.class);

        verify(transactionRepositoryPort, never()).findRecent(anyInt());
    }
}
