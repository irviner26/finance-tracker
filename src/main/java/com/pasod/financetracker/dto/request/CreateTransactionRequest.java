package com.pasod.financetracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateTransactionRequest(
        @NotNull
        BigDecimal amount,

        @NotBlank
        String name,

        String description,

        @NotNull
        Boolean isIncome,

        @NotNull
        LocalDateTime date,

        String itemId
) {
}
