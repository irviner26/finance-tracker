package com.pasod.financetracker.dto.response;

import java.math.BigDecimal;

public record BalanceResponse(
        BigDecimal balance,
        BigDecimal income,
        BigDecimal expense
) {
}
