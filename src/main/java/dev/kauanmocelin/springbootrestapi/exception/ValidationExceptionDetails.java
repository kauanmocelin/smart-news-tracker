package dev.kauanmocelin.springbootrestapi.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {
    private final List<FieldErrorMessage> fieldsErrorValidation;
}
