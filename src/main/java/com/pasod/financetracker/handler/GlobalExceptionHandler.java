package com.pasod.financetracker.handler;

import com.pasod.financetracker.dto.response.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> responseStatus(
            ResponseStatusException e
    ) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(
                        new WebResponse<>(
                                null,
                                e.getStatusCode().value(),
                                e.getStatusCode().toString(),
                                e.getReason(),
                                null,
                                null
                        )
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<String>> constraintViolation(
            ConstraintViolationException e
    ) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        new WebResponse<>(
                                null,
                                BAD_REQUEST.value(),
                                BAD_REQUEST.getReasonPhrase(),
                                e.getMessage(),
                                null,
                                null
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<String>> general(
            Exception e
    ) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        new WebResponse<>(
                                null,
                                BAD_REQUEST.value(),
                                BAD_REQUEST.getReasonPhrase(),
                                e.getMessage(),
                                null,
                                null
                        )
                );
    }
}
