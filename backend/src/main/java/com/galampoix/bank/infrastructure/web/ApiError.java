package com.galampoix.bank.infrastructure.web;

/**
 * Corps de réponse générique décrivant une erreur retournée par l'API.
 *
 * @param message message d'erreur destiné à être affiché ou journalisé
 */
public record ApiError(String message) {
}
