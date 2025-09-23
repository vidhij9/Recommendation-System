package recommendationSystem.contentFilters.tracktrace.exception;

public class DuplicateTrackingNumberException extends RuntimeException {
    public DuplicateTrackingNumberException(String trackingNumber) {
        super("Tracking number '" + trackingNumber + "' already exists");
    }
}
