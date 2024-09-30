package com.pasod.financetracker.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidationHandler {

    private final ValidatorFactory factory =
            Validation.buildDefaultValidatorFactory();

    private final Validator validator =
            factory.getValidator();

    public void validate(Object o) {
        Set<ConstraintViolation<Object>> violations = validator.validate(o);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
