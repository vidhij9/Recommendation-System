package recommendationSystem.contentFilters.tracktrace.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import recommendationSystem.contentFilters.tracktrace.model.ShipmentStatus;

/**
 * Complete representation of a shipment with its events.
 */
public class ShipmentResponse {

  private final UUID id;
  private final String trackingNumber;
  private final String reference;
  private final String origin;
  private final String destination;
  private final String carrier;
  private final String serviceLevel;
  private final String recipient;
  private final String description;
  private final String customerName;
  private final ShipmentStatus status;
  private final LocalDate expectedDeliveryDate;
  private final Instant createdAt;
  private final Instant updatedAt;
  private final List<TrackingEventResponse> events;

  public ShipmentResponse(
      UUID id,
      String trackingNumber,
      String reference,
      String origin,
      String destination,
      String carrier,
      String serviceLevel,
      String recipient,
      String description,
      String customerName,
      ShipmentStatus status,
      LocalDate expectedDeliveryDate,
      Instant createdAt,
      Instant updatedAt,
      List<TrackingEventResponse> events) {
    this.id = id;
    this.trackingNumber = trackingNumber;
    this.reference = reference;
    this.origin = origin;
    this.destination = destination;
    this.carrier = carrier;
    this.serviceLevel = serviceLevel;
    this.recipient = recipient;
    this.description = description;
    this.customerName = customerName;
    this.status = status;
    this.expectedDeliveryDate = expectedDeliveryDate;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.events = events;
  }

  public UUID getId() {
    return id;
  }

  public String getTrackingNumber() {
    return trackingNumber;
  }

  public String getReference() {
    return reference;
  }

  public String getOrigin() {
    return origin;
  }

  public String getDestination() {
    return destination;
  }

  public String getCarrier() {
    return carrier;
  }

  public String getServiceLevel() {
    return serviceLevel;
  }

  public String getRecipient() {
    return recipient;
  }

  public String getDescription() {
    return description;
  }

  public String getCustomerName() {
    return customerName;
  }

  public ShipmentStatus getStatus() {
    return status;
  }

  public LocalDate getExpectedDeliveryDate() {
    return expectedDeliveryDate;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public List<TrackingEventResponse> getEvents() {
    return events;
  }
}
