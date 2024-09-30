package com.pasod.financetracker.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreatePlanRequest(
        @NotBlank
        String name,

        String description
) {
}
