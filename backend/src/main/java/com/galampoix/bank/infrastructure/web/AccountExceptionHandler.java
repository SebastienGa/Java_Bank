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
 * Gestionnaire global des exceptions métier pour les endpoints de l'API,
 * traduisant les exceptions de domaine en réponses HTTP cohérentes.
 */
@RestControllerAdvice
public class AccountExceptionHandler {

    /**
     * Traduit une ressource introuvable en réponse {@code 404 Not Found}.
     *
     * @param e exception de type {@link AccountNotFoundException} ou
     *          {@link ClientNotFoundException}
     * @return une réponse {@code 404 Not Found} contenant le message de l'erreur
     */
    @ExceptionHandler({AccountNotFoundException.class, ClientNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(e.getMessage()));
    }

    /**
     * Traduit un virement invalide en réponse {@code 400 Bad Request}.
     *
     * @param e exception de type {@link InsufficientFundsException} ou
     *          {@link SameAccountTransferException}
     * @return une réponse {@code 400 Bad Request} contenant le message de l'erreur
     */
    @ExceptionHandler({InsufficientFundsException.class, SameAccountTransferException.class})
    public ResponseEntity<ApiError> handleInvalidTransfer(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(e.getMessage()));
    }

    /**
     * Traduit un argument invalide en réponse {@code 400 Bad Request}.
     *
     * @param e exception levée lors de la validation d'un argument
     * @return une réponse {@code 400 Bad Request} contenant le message de l'erreur
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(e.getMessage()));
    }
}
