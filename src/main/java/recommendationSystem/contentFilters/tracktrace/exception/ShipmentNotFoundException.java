package recommendationSystem.contentFilters.tracktrace.exception;

public class ShipmentNotFoundException extends RuntimeException {
    public ShipmentNotFoundException(String trackingNumber) {
        super("Shipment with tracking number '" + trackingNumber + "' was not found");
    }
}
