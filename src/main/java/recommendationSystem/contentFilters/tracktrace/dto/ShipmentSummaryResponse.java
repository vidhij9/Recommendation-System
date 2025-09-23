package recommendationSystem.contentFilters.tracktrace.dto;

import recommendationSystem.contentFilters.tracktrace.model.Shipment;

import java.time.Instant;

public class ShipmentSummaryResponse {
    private final String trackingNumber;
    private final String recipientName;
    private final String destination;
    private final String currentStatus;
    private final Instant createdAt;

    public ShipmentSummaryResponse(Shipment shipment) {
        this.trackingNumber = shipment.getTrackingNumber();
        this.recipientName = shipment.getRecipientName();
        this.destination = shipment.getDestination();
        this.currentStatus = shipment.getCurrentStatus().name();
        this.createdAt = shipment.getCreatedAt();
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

    public String getCurrentStatus() {
        return currentStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
