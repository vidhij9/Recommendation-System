package recommendationSystem.contentFilters.tracktrace.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Immutable representation of a milestone in a shipment's journey.
 */
public class TrackingEvent {

  private final UUID id;
  private final ShipmentStatus status;
  private final Instant timestamp;
  private final String location;
  private final String summary;
  private final String details;
  private final String reportedBy;
  private final boolean customerVisible;
  private final Double latitude;
  private final Double longitude;

  public TrackingEvent(
      UUID id,
      ShipmentStatus status,
      Instant timestamp,
      String location,
      String summary,
      String details,
      String reportedBy,
      boolean customerVisible,
      Double latitude,
      Double longitude) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.status = Objects.requireNonNull(status, "status must not be null");
    this.timestamp = Objects.requireNonNull(timestamp, "timestamp must not be null");
    this.location = location;
    this.summary = summary;
    this.details = details;
    this.reportedBy = reportedBy;
    this.customerVisible = customerVisible;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public static TrackingEvent creationEvent(Instant timestamp, String location, String description) {
    return new TrackingEvent(
        UUID.randomUUID(),
        ShipmentStatus.CREATED,
        timestamp,
        location,
        description != null ? description : "Shipment created",
        null,
        "system",
        true,
        null,
        null);
  }

  public UUID getId() {
    return id;
  }

  public ShipmentStatus getStatus() {
    return status;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public String getLocation() {
    return location;
  }

  public String getSummary() {
    return summary;
  }

  public String getDetails() {
    return details;
  }

  public String getReportedBy() {
    return reportedBy;
  }

  public boolean isCustomerVisible() {
    return customerVisible;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }
}
