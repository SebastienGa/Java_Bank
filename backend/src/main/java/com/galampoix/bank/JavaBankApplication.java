package com.galampoix.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Java Bank.
 * <p>
 * Démarre le contexte Spring Boot et amorce l'ensemble des couches de
 * l'application (domaine, cas d'utilisation, infrastructure web et
 * persistance).
 */
@SpringBootApplication
public class JavaBankApplication {

    /**
     * Lance l'application Spring Boot.
     *
     * @param args arguments de la ligne de commande transmis à Spring Boot
     */
    public static void main(String[] args) {
        SpringApplication.run(JavaBankApplication.class, args);
    }
}
