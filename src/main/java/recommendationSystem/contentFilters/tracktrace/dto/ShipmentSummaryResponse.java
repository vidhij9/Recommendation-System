package recommendationSystem.contentFilters.tracktrace.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import recommendationSystem.contentFilters.tracktrace.model.ShipmentStatus;

/**
 * Lightweight projection returned when listing shipments.
 */
public class ShipmentSummaryResponse {

  private final UUID id;
  private final String trackingNumber;
  private final String origin;
  private final String destination;
  private final ShipmentStatus status;
  private final LocalDate expectedDeliveryDate;
  private final Instant updatedAt;
  private final String carrier;
  private final String serviceLevel;

  public ShipmentSummaryResponse(
      UUID id,
      String trackingNumber,
      String origin,
      String destination,
      ShipmentStatus status,
      LocalDate expectedDeliveryDate,
      Instant updatedAt,
      String carrier,
      String serviceLevel) {
    this.id = id;
    this.trackingNumber = trackingNumber;
    this.origin = origin;
    this.destination = destination;
    this.status = status;
    this.expectedDeliveryDate = expectedDeliveryDate;
    this.updatedAt = updatedAt;
    this.carrier = carrier;
    this.serviceLevel = serviceLevel;
  }

  public UUID getId() {
    return id;
  }

  public String getTrackingNumber() {
    return trackingNumber;
  }

  public String getOrigin() {
    return origin;
  }

  public String getDestination() {
    return destination;
  }

  public ShipmentStatus getStatus() {
    return status;
  }

  public LocalDate getExpectedDeliveryDate() {
    return expectedDeliveryDate;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public String getCarrier() {
    return carrier;
  }

  public String getServiceLevel() {
    return serviceLevel;
  }
}
