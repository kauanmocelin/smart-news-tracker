package dev.kauanmocelin.springbootrestapi.customer.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected String title;
    protected String details;
    protected String developerMessage;
    protected LocalDateTime timestamp;
    protected String path;
}
