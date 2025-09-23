package recommendationSystem.contentFilters.tracktrace.model;

import java.time.Instant;
import java.util.Objects;

public class TrackingEvent {
    private final Instant timestamp;
    private final String location;
    private final String description;
    private final ShipmentStatus status;

    public TrackingEvent(Instant timestamp, String location, String description, ShipmentStatus status) {
        this.timestamp = Objects.requireNonNull(timestamp, "timestamp");
        this.location = Objects.requireNonNull(location, "location");
        this.description = Objects.requireNonNull(description, "description");
        this.status = Objects.requireNonNull(status, "status");
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
