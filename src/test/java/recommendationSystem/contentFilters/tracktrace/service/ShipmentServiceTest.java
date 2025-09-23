package recommendationSystem.contentFilters.tracktrace.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import recommendationSystem.contentFilters.tracktrace.exception.ShipmentNotFoundException;
import recommendationSystem.contentFilters.tracktrace.model.Shipment;
import recommendationSystem.contentFilters.tracktrace.model.ShipmentStatus;
import recommendationSystem.contentFilters.tracktrace.model.TrackingEvent;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShipmentServiceTest {

    private ShipmentService shipmentService;
    private TestTrackingNumberGenerator trackingNumberGenerator;
    private Clock fixedClock;

    @BeforeEach
    void setUp() {
        trackingNumberGenerator = new TestTrackingNumberGenerator();
        fixedClock = Clock.fixed(Instant.parse("2024-01-01T10:15:30Z"), ZoneOffset.UTC);
        shipmentService = new ShipmentService(trackingNumberGenerator, fixedClock);
    }

    @Test
    void createsShipmentWithGeneratedTrackingNumber() {
        trackingNumberGenerator.next("ABC123456789");

        Shipment shipment = shipmentService.createShipment("Jane Doe", "New York", ShipmentStatus.CREATED);

        assertThat(shipment.getTrackingNumber()).isEqualTo("ABC123456789");
        assertThat(shipment.getRecipientName()).isEqualTo("Jane Doe");
        assertThat(shipment.getDestination()).isEqualTo("New York");
        assertThat(shipment.getCreatedAt()).isEqualTo(Instant.parse("2024-01-01T10:15:30Z"));
        assertThat(shipment.getCurrentStatus()).isEqualTo(ShipmentStatus.CREATED);
    }

    @Test
    void addTrackingEventUpdatesCurrentStatus() {
        trackingNumberGenerator.next("TRACK0000001");
        Shipment shipment = shipmentService.createShipment("Alex", "Berlin", ShipmentStatus.CREATED);

        TrackingEvent event = shipmentService.addTrackingEvent(
                shipment.getTrackingNumber(),
                Instant.parse("2024-01-02T12:00:00Z"),
                "Berlin Facility",
                "Package arrived at facility",
                ShipmentStatus.IN_TRANSIT);

        assertThat(event.getStatus()).isEqualTo(ShipmentStatus.IN_TRANSIT);
        assertThat(shipment.getCurrentStatus()).isEqualTo(ShipmentStatus.IN_TRANSIT);
        List<TrackingEvent> events = shipment.getTrackingEvents();
        assertThat(events).hasSize(1);
        assertThat(events.get(0).getLocation()).isEqualTo("Berlin Facility");
    }

    @Test
    void throwsWhenShipmentNotFound() {
        assertThatThrownBy(() -> shipmentService.getShipment("UNKNOWN"))
                .isInstanceOf(ShipmentNotFoundException.class)
                .hasMessageContaining("UNKNOWN");
    }

    @Test
    void returnsDefensiveCopyOfShipments() {
        trackingNumberGenerator.next("TRACK1");
        shipmentService.createShipment("Jane", "Paris", ShipmentStatus.CREATED);

        List<Shipment> shipments = shipmentService.getAllShipments();
        assertThat(shipments).hasSize(1);
        assertThatThrownBy(() -> shipments.add(null)).isInstanceOf(UnsupportedOperationException.class);
    }

    private static class TestTrackingNumberGenerator extends TrackingNumberGenerator {
        private String next;

        void next(String value) {
            this.next = value;
        }

        @Override
        public String generate() {
            if (next == null) {
                return super.generate();
            }
            String value = next;
            next = null;
            return value;
        }
    }
}
