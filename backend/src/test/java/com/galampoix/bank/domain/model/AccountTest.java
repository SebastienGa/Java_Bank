package com.galampoix.bank.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    @Test
    void crediter_augmente_le_solde() {
        Account compte = new Account(UUID.randomUUID(), "Alice", 1000L);

        Account credite = compte.crediter(500L);

        assertThat(credite.soldeCentimes()).isEqualTo(1500L);
    }

    @Test
    void crediter_refuse_un_montant_nul() {
        Account compte = new Account(UUID.randomUUID(), "Alice", 1000L);

        assertThatThrownBy(() -> compte.crediter(0L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void crediter_refuse_un_montant_negatif() {
        Account compte = new Account(UUID.randomUUID(), "Alice", 1000L);

        assertThatThrownBy(() -> compte.crediter(-100L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void debiter_diminue_le_solde() {
        Account compte = new Account(UUID.randomUUID(), "Bob", 300L);

        Account debite = compte.debiter(200L);

        assertThat(debite.soldeCentimes()).isEqualTo(100L);
    }

    @Test
    void debiter_refuse_de_passer_le_solde_en_negatif() {
        Account compte = new Account(UUID.randomUUID(), "Bob", 300L);

        assertThatThrownBy(() -> compte.debiter(400L))
                .isInstanceOf(IllegalStateException.class);
    }
}
