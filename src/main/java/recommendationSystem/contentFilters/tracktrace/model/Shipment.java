package recommendationSystem.contentFilters.tracktrace.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Shipment {
    private final String trackingNumber;
    private final String recipientName;
    private final String destination;
    private final Instant createdAt;
    private volatile ShipmentStatus currentStatus;
    private final List<TrackingEvent> trackingEvents = new CopyOnWriteArrayList<>();

    public Shipment(String trackingNumber, String recipientName, String destination, Instant createdAt, ShipmentStatus initialStatus) {
        this.trackingNumber = Objects.requireNonNull(trackingNumber, "trackingNumber");
        this.recipientName = Objects.requireNonNull(recipientName, "recipientName");
        this.destination = Objects.requireNonNull(destination, "destination");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
        this.currentStatus = Objects.requireNonNull(initialStatus, "initialStatus");
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

    public ShipmentStatus getCurrentStatus() {
        return currentStatus;
    }

    public List<TrackingEvent> getTrackingEvents() {
        List<TrackingEvent> eventsCopy = new ArrayList<>(trackingEvents);
        eventsCopy.sort(Comparator.comparing(TrackingEvent::getTimestamp));
        return Collections.unmodifiableList(eventsCopy);
    }

    public void addTrackingEvent(TrackingEvent event) {
        trackingEvents.add(Objects.requireNonNull(event, "event"));
        this.currentStatus = event.getStatus();
    }
}
