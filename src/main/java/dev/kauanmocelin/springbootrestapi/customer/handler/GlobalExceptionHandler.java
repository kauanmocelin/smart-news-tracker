package dev.kauanmocelin.springbootrestapi.customer.handler;

import dev.kauanmocelin.springbootrestapi.customer.exception.BadRequestException;
import dev.kauanmocelin.springbootrestapi.customer.exception.BadRequestExceptionDetails;
import dev.kauanmocelin.springbootrestapi.customer.exception.FieldErrorMessage;
import dev.kauanmocelin.springbootrestapi.customer.exception.ValidationExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public BadRequestExceptionDetails handleBadRequestException(BadRequestException bre, WebRequest request) {
        return BadRequestExceptionDetails.builder()
            .timestamp(LocalDateTime.now())
            .title("Bad Request Exception, check de documentation")
            .details(bre.getMessage())
            .path(request.getDescription(false))
            .developerMessage(bre.getClass().getName())
            .build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<FieldErrorMessage> fieldsErrorMessage = fieldErrors.stream()
            .map(field -> new FieldErrorMessage(field.getField(), field.getDefaultMessage()))
            .collect(Collectors.toList());

        return new ResponseEntity<>(
            ValidationExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .title("Bad Request Exception, check de documentation")
                .details("Check the field(s) error")
                .path(request.getDescription(false))
                .developerMessage(ex.getClass().getName())
                .fieldsErrorValidation(fieldsErrorMessage)
                .build(), HttpStatus.BAD_REQUEST);
    }
}
