package io.klustr.exceptions;

import com.google.common.collect.Maps;
import jakarta.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@RestControllerAdvice
@ControllerAdvice
@Component
public class StandardResponseExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(StandardResponseExceptionHandler.class);

    @ExceptionHandler(StandardResponseException.class)
    public ResponseEntity<Object> handleCustomException(StandardResponseException ex, HttpServletRequest request) {
        // Log full info
        HttpStatusCode statusCode = ex.getStatusCode();
        if (statusCode.is5xxServerError()) {
            log.error("5xx - Exception [{}]: {}", request.getRequestURI(), ex.getMessage(), ex);
        }

        // Create a response body with error ID
        Map<String, Object> attributes = Maps.newConcurrentMap();

        attributes.put("status", statusCode.value());
        ConcurrentMap<Object, Object> err = Maps.newConcurrentMap();
        if (ex.getErrorId() != null) {
            err.put("id", ex.getErrorId());
            }
        if (ex.getReason() != null) {
            err.put("reason", ex.getReason());
        }
        attributes.put("error", err);
        attributes.put("metadata", ex.getMetadata());
        return ResponseEntity.status(statusCode.value())
                             .contentType(MediaType.valueOf("application/json"))
                            .body(attributes);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleCustomException(ResponseStatusException ex, HttpServletRequest request) {
        HttpStatusCode statusCode = ex.getStatusCode();
        if (statusCode.is5xxServerError()) {
            log.error("5xx - Exception [{}]: {}", request.getRequestURI(), ex.getMessage(), ex);
        }

        // Create a response body with error ID
        Map<String, Object> attributes = Maps.newConcurrentMap();
        attributes.put("status", statusCode.value());
        ConcurrentMap<Object, Object> err = Maps.newConcurrentMap();
        if (ex != null && ex.getReason() != null) {
            err.put("reason", ex.getReason());
        }
        attributes.put("error", err);
        return ResponseEntity.status(statusCode.value())
                .contentType(MediaType.valueOf("application/json"))
                .body(attributes);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex, HttpServletRequest request) {
        if (ex instanceof  StandardResponseException) {
            return handleCustomException((StandardResponseException)ex, request);
        }
        if (ex instanceof ResponseStatusException) {
            return handleCustomException((ResponseStatusException)ex, request);
        }
        // Log full info
        log.error("Unhandled exception at [{}]: {}", request.getRequestURI(), ex.getMessage(), ex);

        // Return structured JSON
        Map<String, Object> body = new HashMap<>();
        body.put("error", ex.getClass().getSimpleName());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        body.put("timestamp", DateTime.now().toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
