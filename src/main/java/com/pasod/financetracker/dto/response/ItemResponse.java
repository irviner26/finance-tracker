package com.pasod.financetracker.dto.response;

import java.math.BigDecimal;

public record ItemResponse(
        String id,
        String name,
        String description,
        BigDecimal target,
        BigDecimal paid
) {
}
