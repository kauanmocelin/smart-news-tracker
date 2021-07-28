package dev.kauanmocelin.springbootrestapi.customer.handler;

import dev.kauanmocelin.springbootrestapi.customer.exception.BadRequestException;
import dev.kauanmocelin.springbootrestapi.customer.exception.BadRequestExceptionDetails;
import dev.kauanmocelin.springbootrestapi.customer.exception.FieldErrorMessage;
import dev.kauanmocelin.springbootrestapi.customer.exception.ValidationExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, check de documentation")
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<FieldErrorMessage> fieldsErrorMessage = fieldErrors.stream()
                .map(field -> new FieldErrorMessage(field.getField(), field.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, check de documentation")
                        .details("Check the field(s) error")
                        .developerMessage(exception.getClass().getName())
                        .fieldsErrorValidation(fieldsErrorMessage)
                        .build(), HttpStatus.BAD_REQUEST);
    }
}
