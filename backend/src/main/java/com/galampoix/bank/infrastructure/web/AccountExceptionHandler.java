package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.domain.exception.AccountNotFoundException;
import com.galampoix.bank.domain.exception.ClientNotFoundException;
import com.galampoix.bank.domain.exception.InsufficientFundsException;
import com.galampoix.bank.domain.exception.SameAccountTransferException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Gestionnaire global des exceptions métier remontées par les contrôleurs
 * REST, traduisant chacune en une réponse HTTP avec un {@link ApiError}
 * décrivant l'erreur.
 */
@RestControllerAdvice
public class AccountExceptionHandler {

    /**
     * Traduit les exceptions de type "ressource introuvable" en réponse {@code 404 Not Found}.
     *
     * @param e exception levée, de type {@link AccountNotFoundException} ou {@link ClientNotFoundException}
     * @return une réponse {@code 404 Not Found} contenant le message de l'exception
     */
    @ExceptionHandler({AccountNotFoundException.class, ClientNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(e.getMessage()));
    }

    /**
     * Traduit les exceptions liées à un virement invalide en réponse {@code 400 Bad Request}.
     *
     * @param e exception levée, de type {@link InsufficientFundsException} ou {@link SameAccountTransferException}
     * @return une réponse {@code 400 Bad Request} contenant le message de l'exception
     */
    @ExceptionHandler({InsufficientFundsException.class, SameAccountTransferException.class})
    public ResponseEntity<ApiError> handleInvalidTransfer(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(e.getMessage()));
    }

    /**
     * Traduit les erreurs de validation d'arguments en réponse {@code 400 Bad Request}.
     *
     * @param e exception d'argument invalide levée par le domaine
     * @return une réponse {@code 400 Bad Request} contenant le message de l'exception
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(e.getMessage()));
    }
}
