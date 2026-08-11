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
class ListLoansForClientUseCaseTest {

    @Mock
    private LoanRepositoryPort loanRepositoryPort;

    @Test
    void execute_retourne_les_prets_du_client() {
        UUID clientId = UUID.randomUUID();
        Loan pret = new Loan(UUID.randomUUID(), clientId, 1000000L, 500000L, 35, 5000L, LocalDate.now());
        when(loanRepositoryPort.findByClientId(clientId)).thenReturn(List.of(pret));

        ListLoansForClientUseCase useCase = new ListLoansForClientUseCase(loanRepositoryPort);

        assertThat(useCase.execute(clientId)).containsExactly(pret);
    }
}
