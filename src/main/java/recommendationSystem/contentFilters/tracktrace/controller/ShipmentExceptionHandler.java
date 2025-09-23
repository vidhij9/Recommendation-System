package recommendationSystem.contentFilters.tracktrace.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import recommendationSystem.contentFilters.tracktrace.exception.DuplicateTrackingNumberException;
import recommendationSystem.contentFilters.tracktrace.exception.ShipmentNotFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ShipmentExceptionHandler {

    @ExceptionHandler(ShipmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleShipmentNotFound(ShipmentNotFoundException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateTrackingNumberException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, String>> handleDuplicateTrackingNumber(DuplicateTrackingNumberException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
