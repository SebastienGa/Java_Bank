package com.galampoix.bank.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionTest {

    private static final Instant DATE = Instant.parse("2026-10-01T10:00:00Z");

    @Test
    void debit_enregistre_un_montant_negatif() {
        UUID accountId = UUID.randomUUID();
        UUID counterpartId = UUID.randomUUID();

        Transaction debit = Transaction.debit(accountId, counterpartId, "Virement vers Bob Durand",
                TransactionCategory.VIREMENT, 300L, DATE);

        assertThat(debit.id()).isNotNull();
        assertThat(debit.accountId()).isEqualTo(accountId);
        assertThat(debit.counterpartAccountId()).isEqualTo(counterpartId);
        assertThat(debit.montantCentimes()).isEqualTo(-300L);
        assertThat(debit.dateOperation()).isEqualTo(DATE);
    }

    @Test
    void credit_enregistre_un_montant_positif() {
        Transaction credit = Transaction.credit(UUID.randomUUID(), UUID.randomUUID(), "Virement de Alice Martin",
                TransactionCategory.VIREMENT, 300L, DATE);

        assertThat(credit.montantCentimes()).isEqualTo(300L);
    }

    @Test
    void accepte_une_operation_sans_compte_contrepartie() {
        Transaction credit = Transaction.credit(UUID.randomUUID(), null, "Salaire",
                TransactionCategory.SALAIRE, 250000L, DATE);

        assertThat(credit.counterpartAccountId()).isNull();
    }

    @Test
    void debit_refuse_un_montant_negatif_ou_nul() {
        assertThatThrownBy(() -> Transaction.debit(UUID.randomUUID(), null, "Virement",
                TransactionCategory.VIREMENT, 0L, DATE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void credit_refuse_un_montant_negatif_ou_nul() {
        assertThatThrownBy(() -> Transaction.credit(UUID.randomUUID(), null, "Virement",
                TransactionCategory.VIREMENT, -100L, DATE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void refuse_un_montant_nul() {
        assertThatThrownBy(() -> new Transaction(UUID.randomUUID(), UUID.randomUUID(), null, "Virement",
                TransactionCategory.VIREMENT, 0L, DATE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void refuse_un_libelle_vide() {
        assertThatThrownBy(() -> new Transaction(UUID.randomUUID(), UUID.randomUUID(), null, " ",
                TransactionCategory.VIREMENT, 100L, DATE))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
