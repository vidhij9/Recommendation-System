package recommendationSystem.contentFilters.tracktrace.service;

import org.springframework.stereotype.Service;

import recommendationSystem.contentFilters.tracktrace.exception.DuplicateTrackingNumberException;
import recommendationSystem.contentFilters.tracktrace.exception.ShipmentNotFoundException;
import recommendationSystem.contentFilters.tracktrace.model.Shipment;
import recommendationSystem.contentFilters.tracktrace.model.ShipmentStatus;
import recommendationSystem.contentFilters.tracktrace.model.TrackingEvent;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShipmentService {
    private final Map<String, Shipment> shipments = new ConcurrentHashMap<>();
    private final TrackingNumberGenerator trackingNumberGenerator;
    private final Clock clock;

    public ShipmentService(TrackingNumberGenerator trackingNumberGenerator) {
        this(trackingNumberGenerator, Clock.systemUTC());
    }

    ShipmentService(TrackingNumberGenerator trackingNumberGenerator, Clock clock) {
        this.trackingNumberGenerator = trackingNumberGenerator;
        this.clock = clock;
    }

    public Shipment createShipment(String recipientName, String destination, ShipmentStatus status) {
        String trackingNumber = trackingNumberGenerator.generate();
        if (shipments.containsKey(trackingNumber)) {
            throw new DuplicateTrackingNumberException(trackingNumber);
        }

        Shipment shipment = new Shipment(trackingNumber, recipientName, destination, Instant.now(clock), status);
        shipments.put(trackingNumber, shipment);
        return shipment;
    }

    public Shipment createShipmentWithTrackingNumber(String trackingNumber, String recipientName, String destination, ShipmentStatus status) {
        if (shipments.containsKey(trackingNumber)) {
            throw new DuplicateTrackingNumberException(trackingNumber);
        }
        Shipment shipment = new Shipment(trackingNumber, recipientName, destination, Instant.now(clock), status);
        shipments.put(trackingNumber, shipment);
        return shipment;
    }

    public List<Shipment> getAllShipments() {
        return Collections.unmodifiableList(new ArrayList<>(shipments.values()));
    }

    public Shipment getShipment(String trackingNumber) {
        Shipment shipment = shipments.get(trackingNumber);
        if (shipment == null) {
            throw new ShipmentNotFoundException(trackingNumber);
        }
        return shipment;
    }

    public TrackingEvent addTrackingEvent(String trackingNumber, Instant eventTime, String location, String description, ShipmentStatus status) {
        Shipment shipment = getShipment(trackingNumber);
        TrackingEvent event = new TrackingEvent(eventTime, location, description, status);
        shipment.addTrackingEvent(event);
        return event;
    }
}
