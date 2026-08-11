package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.AccountRepositoryPort;
import com.galampoix.bank.domain.exception.AccountNotFoundException;
import com.galampoix.bank.domain.exception.InsufficientFundsException;
import com.galampoix.bank.domain.exception.SameAccountTransferException;
import com.galampoix.bank.domain.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferMoneyUseCaseTest {

    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    private TransferMoneyUseCase transferMoneyUseCase;

    @BeforeEach
    void setUp() {
        transferMoneyUseCase = new TransferMoneyUseCase(accountRepositoryPort);
    }

    @Test
    void execute_debite_la_source_et_credite_la_destination() {
        Account source = new Account(UUID.randomUUID(), UUID.randomUUID(), 1000L);
        Account destination = new Account(UUID.randomUUID(), UUID.randomUUID(), 200L);

        when(accountRepositoryPort.findById(source.id())).thenReturn(Optional.of(source));
        when(accountRepositoryPort.findById(destination.id())).thenReturn(Optional.of(destination));
        when(accountRepositoryPort.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        transferMoneyUseCase.execute(source.id(), destination.id(), 300L);

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepositoryPort, times(2)).save(captor.capture());

        Account sourceSauvegardee = captor.getAllValues().get(0);
        Account destinationSauvegardee = captor.getAllValues().get(1);

        assertThat(sourceSauvegardee.soldeCentimes()).isEqualTo(700L);
        assertThat(destinationSauvegardee.soldeCentimes()).isEqualTo(500L);
    }

    @Test
    void execute_refuse_un_virement_vers_soi_meme() {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, UUID.randomUUID(), 1000L);

        when(accountRepositoryPort.findById(accountId)).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> transferMoneyUseCase.execute(accountId, accountId, 100L))
                .isInstanceOf(SameAccountTransferException.class);

        verify(accountRepositoryPort, never()).save(any());
    }

    @Test
    void execute_refuse_un_virement_si_le_compte_source_est_introuvable() {
        UUID sourceId = UUID.randomUUID();
        UUID destinationId = UUID.randomUUID();

        when(accountRepositoryPort.findById(sourceId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transferMoneyUseCase.execute(sourceId, destinationId, 100L))
                .isInstanceOf(AccountNotFoundException.class);

        verify(accountRepositoryPort, never()).save(any());
    }

    @Test
    void execute_refuse_un_virement_si_le_compte_destination_est_introuvable() {
        Account source = new Account(UUID.randomUUID(), UUID.randomUUID(), 1000L);
        UUID destinationId = UUID.randomUUID();

        when(accountRepositoryPort.findById(source.id())).thenReturn(Optional.of(source));
        when(accountRepositoryPort.findById(destinationId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transferMoneyUseCase.execute(source.id(), destinationId, 100L))
                .isInstanceOf(AccountNotFoundException.class);

        verify(accountRepositoryPort, never()).save(any());
    }

    @Test
    void execute_refuse_un_virement_si_le_solde_source_est_insuffisant() {
        Account source = new Account(UUID.randomUUID(), UUID.randomUUID(), 100L);
        Account destination = new Account(UUID.randomUUID(), UUID.randomUUID(), 200L);

        when(accountRepositoryPort.findById(source.id())).thenReturn(Optional.of(source));
        when(accountRepositoryPort.findById(destination.id())).thenReturn(Optional.of(destination));

        assertThatThrownBy(() -> transferMoneyUseCase.execute(source.id(), destination.id(), 500L))
                .isInstanceOf(InsufficientFundsException.class);

        verify(accountRepositoryPort, never()).save(any());
    }
}
