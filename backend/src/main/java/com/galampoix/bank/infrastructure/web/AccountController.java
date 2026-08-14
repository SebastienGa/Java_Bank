package com.galampoix.bank.infrastructure.web;

import com.galampoix.bank.application.usecase.GetAccountByIdUseCase;
import com.galampoix.bank.application.usecase.GetClientUseCase;
import com.galampoix.bank.application.usecase.ListAccountsUseCase;
import com.galampoix.bank.application.usecase.ListClientsUseCase;
import com.galampoix.bank.application.usecase.TransferMoneyUseCase;
import com.galampoix.bank.domain.exception.ClientNotFoundException;
import com.galampoix.bank.domain.model.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Contrôleur REST exposant les opérations liées aux comptes bancaires
 * sous {@code /api/accounts}.
 * <p>
 * Les erreurs métier levées par les cas d'utilisation (compte/client
 * introuvable, virement invalide, etc.) sont traduites en réponses HTTP
 * par {@link AccountExceptionHandler}.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final ListAccountsUseCase listAccountsUseCase;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final TransferMoneyUseCase transferMoneyUseCase;
    private final ListClientsUseCase listClientsUseCase;
    private final GetClientUseCase getClientUseCase;

    public AccountController(ListAccountsUseCase listAccountsUseCase,
                              GetAccountByIdUseCase getAccountByIdUseCase,
                              TransferMoneyUseCase transferMoneyUseCase,
                              ListClientsUseCase listClientsUseCase,
                              GetClientUseCase getClientUseCase) {
        this.listAccountsUseCase = listAccountsUseCase;
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.transferMoneyUseCase = transferMoneyUseCase;
        this.listClientsUseCase = listClientsUseCase;
        this.getClientUseCase = getClientUseCase;
    }

    /**
     * Liste l'ensemble des comptes, enrichis des informations du client
     * titulaire.
     *
     * @return la représentation web de tous les comptes
     */
    @GetMapping
    public List<AccountResponse> listAccounts() {
        Map<UUID, Client> clientsById = listClientsUseCase.execute().stream()
                .collect(Collectors.toMap(Client::id, Function.identity()));
        return listAccountsUseCase.execute().stream()
                .map(account -> AccountWebMapper.toResponse(account, clientsById.get(account.clientId())))
                .toList();
    }

    /**
     * Récupère un compte par son identifiant, enrichi des informations du
     * client titulaire.
     *
     * @param id identifiant du compte recherché
     * @return {@code 200 OK} avec le compte trouvé, ou {@code 404 Not Found}
     *         si aucun compte ne correspond à cet identifiant
     * @throws ClientNotFoundException si le compte existe mais que le client
     *                                 titulaire associé est introuvable
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable UUID id) {
        return getAccountByIdUseCase.execute(id)
                .map(account -> {
                    Client client = getClientUseCase.execute(account.clientId())
                            .orElseThrow(() -> new ClientNotFoundException(account.clientId()));
                    return AccountWebMapper.toResponse(account, client);
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Effectue un virement depuis le compte identifié par {@code id} vers
     * le compte destination indiqué dans la requête.
     *
     * @param id      identifiant du compte source du virement
     * @param request détails du virement (compte destination et montant)
     * @return {@code 204 No Content} si le virement a été effectué avec succès
     */
    @PostMapping("/{id}/transfer")
    public ResponseEntity<Void> transfer(@PathVariable UUID id, @RequestBody TransferRequest request) {
        transferMoneyUseCase.execute(id, request.destinationAccountId(), request.montantCentimes());
        return ResponseEntity.noContent().build();
    }
}
