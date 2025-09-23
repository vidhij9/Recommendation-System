package recommendationSystem.contentFilters.tracktrace.dto;

import recommendationSystem.contentFilters.tracktrace.model.ShipmentStatus;
import recommendationSystem.contentFilters.tracktrace.model.TrackingEvent;

import java.time.Instant;

public class TrackingEventResponse {
    private final Instant timestamp;
    private final String location;
    private final String description;
    private final ShipmentStatus status;

    public TrackingEventResponse(TrackingEvent event) {
        this.timestamp = event.getTimestamp();
        this.location = event.getLocation();
        this.description = event.getDescription();
        this.status = event.getStatus();
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public ShipmentStatus getStatus() {
        return status;
    }
}
