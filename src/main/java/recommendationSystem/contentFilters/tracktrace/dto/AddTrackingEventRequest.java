package recommendationSystem.contentFilters.tracktrace.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import recommendationSystem.contentFilters.tracktrace.model.ShipmentStatus;

/**
 * Payload to register a new tracking event for an existing shipment.
 */
public class AddTrackingEventRequest {

  @NotNull(message = "Status is required")
  private ShipmentStatus status;

  @NotBlank(message = "Location is required")
  private String location;

  @NotBlank(message = "Summary is required")
  private String summary;

  private String details;
  private String reportedBy;
  private Boolean customerVisible;
  private Instant timestamp;
  private Double latitude;
  private Double longitude;
  private LocalDate expectedDeliveryDate;

  public ShipmentStatus getStatus() {
    return status;
  }

  public void setStatus(ShipmentStatus status) {
    this.status = status;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getReportedBy() {
    return reportedBy;
  }

  public void setReportedBy(String reportedBy) {
    this.reportedBy = reportedBy;
  }

  public Boolean getCustomerVisible() {
    return customerVisible;
  }

  public void setCustomerVisible(Boolean customerVisible) {
    this.customerVisible = customerVisible;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public LocalDate getExpectedDeliveryDate() {
    return expectedDeliveryDate;
  }

  public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
    this.expectedDeliveryDate = expectedDeliveryDate;
  }
}
