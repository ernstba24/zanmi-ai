package com.zanmi.ai.api;

import com.zanmi.ai.application.ChatProviderException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ErrorResponse.FieldViolation> violations =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(e ->
                                new ErrorResponse.FieldViolation(
                                        e.getField(), e.getDefaultMessage()))
                        .toList();
        return build(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                "The request contains invalid fields",
                request,
                violations);
    }

    // The exception message can echo parts of the payload, so it is never returned to the client.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        return build(
                HttpStatus.BAD_REQUEST,
                "MALFORMED_REQUEST",
                "The request body is missing or malformed",
                request,
                null);
    }

    // Only the reason and exception class are logged: provider messages can contain key fragments.
    @ExceptionHandler(ChatProviderException.class)
    public ResponseEntity<ErrorResponse> handleProvider(
            ChatProviderException ex, HttpServletRequest request) {
        log.warn(
                "Chat provider failure: reason={}, cause={}",
                ex.getReason(),
                ex.getCause() == null ? "none" : ex.getCause().getClass().getName());
        return switch (ex.getReason()) {
            case TIMEOUT ->
                    build(
                            HttpStatus.GATEWAY_TIMEOUT,
                            "PROVIDER_TIMEOUT",
                            "The AI provider did not respond in time",
                            request,
                            null);
            case UNAVAILABLE ->
                    build(
                            HttpStatus.SERVICE_UNAVAILABLE,
                            "PROVIDER_UNAVAILABLE",
                            "The AI provider is temporarily unavailable",
                            request,
                            null);
            case REJECTED ->
                    build(
                            HttpStatus.BAD_GATEWAY,
                            "PROVIDER_ERROR",
                            "The AI provider could not process the request",
                            request,
                            null);
        };
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(
            Exception ex, HttpServletRequest request) {
        // Spring's own web exceptions (405, 415, 404, ...) already carry the correct status.
        if (ex instanceof org.springframework.web.ErrorResponse springError
                && springError.getStatusCode() instanceof HttpStatus status) {
            return build(
                    status, "REQUEST_ERROR", "The request could not be processed", request, null);
        }
        log.error("Unexpected error: {}", ex.getClass().getName());
        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "An unexpected error occurred",
                request,
                null);
    }

    private ResponseEntity<ErrorResponse> build(
            HttpStatus status,
            String code,
            String message,
            HttpServletRequest request,
            List<ErrorResponse.FieldViolation> fieldErrors) {
        ErrorResponse body =
                new ErrorResponse(
                        Instant.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        code,
                        message,
                        request.getRequestURI(),
                        fieldErrors);
        return ResponseEntity.status(status).body(body);
    }
}
