package com.galampoix.bank.infrastructure.web;

/**
 * Corps de réponse JSON standard utilisé pour décrire une erreur renvoyée
 * par l'API REST.
 *
 * @param message message décrivant l'erreur survenue
 */
public record ApiError(String message) {
}
