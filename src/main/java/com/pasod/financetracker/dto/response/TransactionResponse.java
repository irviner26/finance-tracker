package com.pasod.financetracker.dto.response;

import java.math.BigDecimal;

public record TransactionResponse(
        String id,
        BigDecimal amount,
        String name,
        String description,
        String date
) {
}
