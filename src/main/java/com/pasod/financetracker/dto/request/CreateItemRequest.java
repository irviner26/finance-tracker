package com.pasod.financetracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateItemRequest(
        @NotBlank
        String name,

        String description,

        @NotNull @Positive
        BigDecimal target
) {
}
