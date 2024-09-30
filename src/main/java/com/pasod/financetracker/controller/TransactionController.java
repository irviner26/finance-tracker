package com.pasod.financetracker.controller;

import com.pasod.financetracker.dto.request.CreateTransactionRequest;
import com.pasod.financetracker.dto.request.EditTransactionRequest;
import com.pasod.financetracker.dto.response.BalanceResponse;
import com.pasod.financetracker.dto.response.TransactionResponse;
import com.pasod.financetracker.dto.response.WebResponse;
import com.pasod.financetracker.service.BalanceService;
import com.pasod.financetracker.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private final BalanceService balanceService;

    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<TransactionResponse>> inputTransaction(
            @RequestBody CreateTransactionRequest request
    ) {
        var response = transactionService.inputTransaction(request);

        return responseEntity(
                response,
                "Successfully created transaction detail",
                null,
                null
        );
    }

    @PatchMapping(
            path = "/edit/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<TransactionResponse>> editTransaction(
            @RequestBody EditTransactionRequest request,
            @PathVariable(value = "id") String id
    ) {
        var response = transactionService.editTransaction(request, id);

        return responseEntity(
                response,
                "Successfully edited transaction detail",
                null,
                null
        );
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<List<TransactionResponse>>> getTransactions(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ) {
        var responses = transactionService.getTransactionsOfUser(page, size);

        return responseEntity(
                responses,
                "Successfully retrieved transaction details",
                page,
                size
        );
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<TransactionResponse>> getTransaction(
            @PathVariable(value = "id") String id
    ) {
        var response = transactionService.getTransaction(id);

        return responseEntity(
                response,
                "Successfully retrieved transaction detail",
                null,
                null
        );
    }

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<String>> deleteTransaction(
            @PathVariable String id
    ) {
        transactionService.deleteTransaction(id);

        return responseEntity(
                "Deleted ".concat(id),
                "Successfully deleted transaction detail",
                null,
                null
        );
    }

    @GetMapping(
            path = "/balance",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<BalanceResponse>> getBalanceInfo() {
        var response = balanceService.getBalanceInfo();

        return responseEntity(
                response,
                "Successfully retrieved balance info",
                null,
                null
        );
    }

    private static <T> ResponseEntity<WebResponse<T>> responseEntity(
            T response,
            String message,
            Integer page,
            Integer size
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                message,
                                null,
                                page,
                                size
                        )
                );
    }
}
