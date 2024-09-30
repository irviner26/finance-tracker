package com.pasod.financetracker.service;

import com.pasod.financetracker.dto.request.CreateTransactionRequest;
import com.pasod.financetracker.dto.request.EditTransactionRequest;
import com.pasod.financetracker.dto.response.TransactionResponse;
import com.pasod.financetracker.handler.ValidationHandler;
import com.pasod.financetracker.model.Transaction;
import com.pasod.financetracker.repository.ItemRepository;
import com.pasod.financetracker.repository.ItemTransactionRepository;
import com.pasod.financetracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceIMPL implements TransactionService{

    public static final String TRANSACTION_NOT_FOUND = "Transaction not found";

    private final TransactionRepository transactionRepository;

    private final ItemRepository itemRepository;

    private final ItemTransactionRepository itemTransactionRepository;

    private final ValidationHandler validationHandler;

    @Override
    public TransactionResponse inputTransaction(CreateTransactionRequest request) {
        validationHandler.validate(request);

        if (!request.itemId().isBlank()) {
            //TODO: validate that the item object is a belonging of the user

            //After validated:


        }

        var transaction =
                Transaction
                        .builder()
                        .name(request.name())
                        .amount(request.amount())
                        .date(request.date())
                        .description(request.description())
                        .isIncome(request.isIncome())
                        .user(null)
                        .build();

        transactionRepository.save(transaction);

        return toResponse(transaction);
    }

    @Override
    @Transactional
    public TransactionResponse editTransaction(EditTransactionRequest request, String id) {
        var transaction = transactionRepository
                .findById(UUID.fromString(id))
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                TRANSACTION_NOT_FOUND
                        )
                );

        if (!request.name().isBlank()) {
            transaction.setName(request.name());
        }

        if (!request.description().isBlank()) {
            transaction.setDescription(request.description());
        }

        if (!request.isIncome().equals(transaction.getIsIncome())) {
            transaction.setIsIncome(request.isIncome());
        }

        if (request.date() != null) {
            transaction.setDate(request.date());
        }

        if (request.amount() != null) {
            transaction.setAmount(request.amount());
        }

        transactionRepository.save(transaction);

        return toResponse(transaction);
    }

    @Override
    public TransactionResponse getTransaction(String id) {
        var transaction = transactionRepository
                .findById(UUID.fromString(id))
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                TRANSACTION_NOT_FOUND
                        )
                );

        return toResponse(transaction);
    }

    @Override
    public List<TransactionResponse> getTransactionsOfUser(int page, int size) {

        if (page < 1 && size < 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page and content numbers cannot be less than 1"
            );
        } else if (page < 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page number cannot be less than 1"
            );
        } else if (size < 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Content size cannot be less than 1"
            );
        }

        List<Transaction> transactions =
                transactionRepository.findByUser(
                        null,
                        PageRequest.of(page - 1, size)
                );

        return transactions
                .stream()
                .map(TransactionServiceIMPL::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteTransaction(String id) {
        var transaction = transactionRepository
                .findById(UUID.fromString(id))
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                TRANSACTION_NOT_FOUND
                        )
                );

        transactionRepository.delete(transaction);
    }

    private static TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId().toString(),
                transaction.getAmount(),
                transaction.getName(),
                transaction.getDescription(),
                transaction.getDate()
                        .format(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                        )
        );
    }
}
