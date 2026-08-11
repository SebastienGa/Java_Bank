package com.galampoix.bank.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoanTest {

    @Test
    void calculerProgression_retourne_le_pourcentage_deja_rembourse() {
        Loan pret = new Loan(UUID.randomUUID(), UUID.randomUUID(), 18000000L, 6300000L, 35, 105000L, LocalDate.of(2016, 6, 1));

        assertThat(pret.calculerProgression()).isEqualTo(65);
    }

    @Test
    void calculerProgression_vaut_zero_quand_rien_n_est_rembourse() {
        Loan pret = new Loan(UUID.randomUUID(), UUID.randomUUID(), 1000000L, 1000000L, 20, 5000L, LocalDate.now());

        assertThat(pret.calculerProgression()).isEqualTo(0);
    }

    @Test
    void calculerProgression_vaut_cent_quand_le_pret_est_solde() {
        Loan pret = new Loan(UUID.randomUUID(), UUID.randomUUID(), 1000000L, 0L, 20, 5000L, LocalDate.now());

        assertThat(pret.calculerProgression()).isEqualTo(100);
    }

    @Test
    void refuse_un_montant_restant_superieur_au_montant_initial() {
        assertThatThrownBy(() -> new Loan(UUID.randomUUID(), UUID.randomUUID(), 1000000L, 1000001L, 20, 5000L, LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void refuse_un_montant_initial_negatif_ou_nul() {
        assertThatThrownBy(() -> new Loan(UUID.randomUUID(), UUID.randomUUID(), 0L, 0L, 20, 5000L, LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
