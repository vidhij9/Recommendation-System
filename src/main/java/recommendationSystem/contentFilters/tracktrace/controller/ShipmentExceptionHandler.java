package recommendationSystem.contentFilters.tracktrace.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import recommendationSystem.contentFilters.tracktrace.exception.DuplicateTrackingNumberException;
import recommendationSystem.contentFilters.tracktrace.exception.ShipmentNotFoundException;

/**
 * Provides consistent API level errors for the shipment endpoints.
 */
@RestControllerAdvice(assignableTypes = ShipmentController.class)
public class ShipmentExceptionHandler {

  @ExceptionHandler(ShipmentNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, Object> handleNotFound(ShipmentNotFoundException exception) {
    Map<String, Object> response = new HashMap<>();
    response.put("error", exception.getMessage());
    response.put("code", HttpStatus.NOT_FOUND.value());
    return response;
  }

  @ExceptionHandler(DuplicateTrackingNumberException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public Map<String, Object> handleDuplicateTrackingNumber(
      DuplicateTrackingNumberException exception) {
    Map<String, Object> response = new HashMap<>();
    response.put("error", exception.getMessage());
    response.put("code", HttpStatus.CONFLICT.value());
    return response;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleValidationErrors(MethodArgumentNotValidException exception) {
    Map<String, Object> response = new HashMap<>();
    response.put("error", "Validation failed");
    response.put("code", HttpStatus.BAD_REQUEST.value());
    response.put(
        "details",
        exception.getBindingResult().getFieldErrors().stream()
            .collect(
                Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (existing, replacement) -> existing)));
    return response;
  }
}
