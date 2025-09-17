package recommendationSystem.contentFilters.tracktrace.dto;

import java.time.Instant;
import java.util.UUID;
import recommendationSystem.contentFilters.tracktrace.model.ShipmentStatus;

/**
 * Representation of an individual tracking event.
 */
public class TrackingEventResponse {

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

  public TrackingEventResponse(
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
    this.id = id;
    this.status = status;
    this.timestamp = timestamp;
    this.location = location;
    this.summary = summary;
    this.details = details;
    this.reportedBy = reportedBy;
    this.customerVisible = customerVisible;
    this.latitude = latitude;
    this.longitude = longitude;
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
