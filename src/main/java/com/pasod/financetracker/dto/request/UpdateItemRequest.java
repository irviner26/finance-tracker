package com.pasod.financetracker.dto.request;

import java.math.BigDecimal;

public record UpdateItemRequest(
        String name,
        String description,
        BigDecimal target,
        BigDecimal paid
) {
}
