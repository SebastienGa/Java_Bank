package com.galampoix.bank.application.usecase;

import com.galampoix.bank.application.port.out.AccountRepositoryPort;
import com.galampoix.bank.application.port.out.ClientRepositoryPort;
import com.galampoix.bank.application.port.out.TransactionRepositoryPort;
import com.galampoix.bank.domain.exception.AccountNotFoundException;
import com.galampoix.bank.domain.exception.ClientNotFoundException;
import com.galampoix.bank.domain.exception.InsufficientFundsException;
import com.galampoix.bank.domain.exception.SameAccountTransferException;
import com.galampoix.bank.domain.model.Account;
import com.galampoix.bank.domain.model.Client;
import com.galampoix.bank.domain.model.Transaction;
import com.galampoix.bank.domain.model.TransactionCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
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

    private static final Instant MAINTENANT = Instant.parse("2026-10-01T10:00:00Z");

    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @Mock
    private TransactionRepositoryPort transactionRepositoryPort;

    private TransferMoneyUseCase transferMoneyUseCase;

    private final Client alice = new Client(UUID.randomUUID(), "Alice", "Martin", "alice.martin@example.com");
    private final Client bob = new Client(UUID.randomUUID(), "Bob", "Durand", "bob.durand@example.com");

    @BeforeEach
    void setUp() {
        transferMoneyUseCase = new TransferMoneyUseCase(accountRepositoryPort, clientRepositoryPort,
                transactionRepositoryPort, Clock.fixed(MAINTENANT, ZoneOffset.UTC));
    }

    @Test
    void execute_debite_la_source_et_credite_la_destination() {
        Account source = new Account(UUID.randomUUID(), alice.id(), 1000L);
        Account destination = new Account(UUID.randomUUID(), bob.id(), 200L);

        stubVirementValide(source, destination);

        transferMoneyUseCase.execute(source.id(), destination.id(), 300L);

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepositoryPort, times(2)).save(captor.capture());

        Account sourceSauvegardee = captor.getAllValues().get(0);
        Account destinationSauvegardee = captor.getAllValues().get(1);

        assertThat(sourceSauvegardee.soldeCentimes()).isEqualTo(700L);
        assertThat(destinationSauvegardee.soldeCentimes()).isEqualTo(500L);
    }

    @Test
    void execute_enregistre_une_operation_de_debit_et_une_de_credit() {
        Account source = new Account(UUID.randomUUID(), alice.id(), 1000L);
        Account destination = new Account(UUID.randomUUID(), bob.id(), 200L);

        stubVirementValide(source, destination);

        transferMoneyUseCase.execute(source.id(), destination.id(), 300L);

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepositoryPort, times(2)).save(captor.capture());

        Transaction debit = captor.getAllValues().get(0);
        Transaction credit = captor.getAllValues().get(1);

        assertThat(debit.accountId()).isEqualTo(source.id());
        assertThat(debit.counterpartAccountId()).isEqualTo(destination.id());
        assertThat(debit.libelle()).isEqualTo("Virement vers Bob Durand");
        assertThat(debit.categorie()).isEqualTo(TransactionCategory.VIREMENT);
        assertThat(debit.montantCentimes()).isEqualTo(-300L);
        assertThat(debit.dateOperation()).isEqualTo(MAINTENANT);

        assertThat(credit.accountId()).isEqualTo(destination.id());
        assertThat(credit.counterpartAccountId()).isEqualTo(source.id());
        assertThat(credit.libelle()).isEqualTo("Virement de Alice Martin");
        assertThat(credit.categorie()).isEqualTo(TransactionCategory.VIREMENT);
        assertThat(credit.montantCentimes()).isEqualTo(300L);
        assertThat(credit.dateOperation()).isEqualTo(MAINTENANT);
    }

    @Test
    void execute_refuse_un_virement_vers_soi_meme() {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, UUID.randomUUID(), 1000L);

        when(accountRepositoryPort.findById(accountId)).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> transferMoneyUseCase.execute(accountId, accountId, 100L))
                .isInstanceOf(SameAccountTransferException.class);

        verify(accountRepositoryPort, never()).save(any());
        verify(transactionRepositoryPort, never()).save(any());
    }

    @Test
    void execute_refuse_un_virement_si_le_compte_source_est_introuvable() {
        UUID sourceId = UUID.randomUUID();
        UUID destinationId = UUID.randomUUID();

        when(accountRepositoryPort.findById(sourceId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transferMoneyUseCase.execute(sourceId, destinationId, 100L))
                .isInstanceOf(AccountNotFoundException.class);

        verify(accountRepositoryPort, never()).save(any());
        verify(transactionRepositoryPort, never()).save(any());
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
        verify(transactionRepositoryPort, never()).save(any());
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
        verify(transactionRepositoryPort, never()).save(any());
    }

    @Test
    void execute_refuse_un_virement_si_un_titulaire_est_introuvable() {
        Account source = new Account(UUID.randomUUID(), alice.id(), 1000L);
        Account destination = new Account(UUID.randomUUID(), UUID.randomUUID(), 200L);

        when(accountRepositoryPort.findById(source.id())).thenReturn(Optional.of(source));
        when(accountRepositoryPort.findById(destination.id())).thenReturn(Optional.of(destination));
        when(clientRepositoryPort.findById(alice.id())).thenReturn(Optional.of(alice));
        when(clientRepositoryPort.findById(destination.clientId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transferMoneyUseCase.execute(source.id(), destination.id(), 300L))
                .isInstanceOf(ClientNotFoundException.class);

        verify(accountRepositoryPort, never()).save(any());
        verify(transactionRepositoryPort, never()).save(any());
    }

    private void stubVirementValide(Account source, Account destination) {
        when(accountRepositoryPort.findById(source.id())).thenReturn(Optional.of(source));
        when(accountRepositoryPort.findById(destination.id())).thenReturn(Optional.of(destination));
        when(clientRepositoryPort.findById(alice.id())).thenReturn(Optional.of(alice));
        when(clientRepositoryPort.findById(bob.id())).thenReturn(Optional.of(bob));
        when(accountRepositoryPort.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transactionRepositoryPort.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }
}
