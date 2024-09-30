package com.pasod.financetracker.dto.response;

public record PlanResponse(
        String id,
        String name,
        String description,
        String creator
) {
}
