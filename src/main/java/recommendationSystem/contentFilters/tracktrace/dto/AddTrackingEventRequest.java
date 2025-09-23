package recommendationSystem.contentFilters.tracktrace.dto;

import recommendationSystem.contentFilters.tracktrace.model.ShipmentStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

public class AddTrackingEventRequest {
    private Instant timestamp;

    @NotBlank(message = "Location is required")
    @Size(max = 120, message = "Location must be at most 120 characters")
    private String location;

    @NotBlank(message = "Description is required")
    @Size(max = 200, message = "Description must be at most 200 characters")
    private String description;

    @NotNull(message = "Status is required")
    private ShipmentStatus status;

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }
}
