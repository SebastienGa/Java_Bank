package com.galampoix.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Spring Boot Java_Bank.
 */
@SpringBootApplication
public class JavaBankApplication {

    /**
     * Démarre l'application Spring Boot.
     *
     * @param args arguments de la ligne de commande, transmis à Spring Boot
     */
    public static void main(String[] args) {
        SpringApplication.run(JavaBankApplication.class, args);
    }
}
