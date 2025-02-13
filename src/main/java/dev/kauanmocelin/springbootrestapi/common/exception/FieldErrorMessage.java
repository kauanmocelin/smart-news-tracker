package dev.kauanmocelin.springbootrestapi.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FieldErrorMessage {
    private final String field;
    private final String message;
}
