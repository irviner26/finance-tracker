package com.pasod.financetracker.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EditTransactionRequest(
        BigDecimal amount,
        String name,
        String description,
        Boolean isIncome,
        LocalDateTime date
) {
}
