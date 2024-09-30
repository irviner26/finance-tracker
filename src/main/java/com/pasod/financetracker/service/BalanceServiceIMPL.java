package com.pasod.financetracker.service;

import com.pasod.financetracker.dto.response.BalanceResponse;
import com.pasod.financetracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceServiceIMPL implements BalanceService{

    private final TransactionRepository transactionRepository;

    @Override
    public BalanceResponse getBalanceInfo() {
        var income = transactionRepository
                .totalIncome()
                .orElse(BigDecimal.valueOf(0.0));

        var expense = transactionRepository
                .totalExpense()
                .orElse(BigDecimal.valueOf(0.0));

        var balance = income.subtract(expense);

        return new BalanceResponse(
                balance,
                income,
                expense
        );
    }
}
