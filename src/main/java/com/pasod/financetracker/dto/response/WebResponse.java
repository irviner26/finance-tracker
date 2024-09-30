package com.pasod.financetracker.dto.response;

public record WebResponse<T>(
        T data,
        Integer code,
        String message,
        String error,
        Integer page,
        Integer size
) {
}
