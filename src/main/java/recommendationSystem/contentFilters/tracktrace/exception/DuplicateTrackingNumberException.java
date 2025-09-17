package recommendationSystem.contentFilters.tracktrace.exception;

/**
 * Thrown when a shipment is created with a tracking number that already exists in the system.
 */
public class DuplicateTrackingNumberException extends RuntimeException {

  public DuplicateTrackingNumberException(String trackingNumber) {
    super("Tracking number already exists: " + trackingNumber);
  }
}
