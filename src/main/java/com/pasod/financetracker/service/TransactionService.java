package com.pasod.financetracker.service;

import com.pasod.financetracker.dto.request.CreateTransactionRequest;
import com.pasod.financetracker.dto.request.EditTransactionRequest;
import com.pasod.financetracker.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse inputTransaction(CreateTransactionRequest request);

    TransactionResponse editTransaction(EditTransactionRequest request, String id);

    TransactionResponse getTransaction(String id);

    List<TransactionResponse> getTransactionsOfUser(int page, int size);

    void deleteTransaction(String id);
}
