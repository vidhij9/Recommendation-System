package recommendationSystem.contentFilters.tracktrace.exception;

import java.util.UUID;

/**
 * Thrown when a shipment cannot be located.
 */
public class ShipmentNotFoundException extends RuntimeException {

  public ShipmentNotFoundException(UUID shipmentId) {
    super("Shipment not found: " + shipmentId);
  }

  public ShipmentNotFoundException(String trackingNumber) {
    super("Shipment not found for tracking number: " + trackingNumber);
  }
}
