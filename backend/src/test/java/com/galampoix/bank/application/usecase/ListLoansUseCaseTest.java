package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.LoanRepositoryPort;
import com.galampoix.bank.domain.model.Loan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListLoansUseCaseTest {

    @Mock
    private LoanRepositoryPort loanRepositoryPort;

    @Test
    void execute_retourne_tous_les_prets() {
        Loan pretAlice = new Loan(UUID.randomUUID(), UUID.randomUUID(), 18000000L, 6300000L, 35, 105000L, LocalDate.of(2016, 6, 1));
        Loan pretBob = new Loan(UUID.randomUUID(), UUID.randomUUID(), 1500000L, 1200000L, 49, 28000L, LocalDate.of(2024, 9, 10));
        when(loanRepositoryPort.findAll()).thenReturn(List.of(pretAlice, pretBob));

        ListLoansUseCase useCase = new ListLoansUseCase(loanRepositoryPort);

        assertThat(useCase.execute()).containsExactly(pretAlice, pretBob);
    }
}
