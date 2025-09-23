package recommendationSystem.contentFilters.tracktrace.dto;

import recommendationSystem.contentFilters.tracktrace.model.Shipment;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class ShipmentResponse {
    private final String trackingNumber;
    private final String recipientName;
    private final String destination;
    private final Instant createdAt;
    private final String currentStatus;
    private final List<TrackingEventResponse> trackingEvents;

    public ShipmentResponse(Shipment shipment) {
        this.trackingNumber = shipment.getTrackingNumber();
        this.recipientName = shipment.getRecipientName();
        this.destination = shipment.getDestination();
        this.createdAt = shipment.getCreatedAt();
        this.currentStatus = shipment.getCurrentStatus().name();
        this.trackingEvents = shipment.getTrackingEvents()
                .stream()
                .map(TrackingEventResponse::new)
                .collect(Collectors.toList());
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getDestination() {
        return destination;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public List<TrackingEventResponse> getTrackingEvents() {
        return trackingEvents;
    }
}
