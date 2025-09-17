package recommendationSystem.contentFilters.tracktrace.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import recommendationSystem.contentFilters.tracktrace.dto.AddTrackingEventRequest;
import recommendationSystem.contentFilters.tracktrace.dto.CreateShipmentRequest;
import recommendationSystem.contentFilters.tracktrace.exception.DuplicateTrackingNumberException;
import recommendationSystem.contentFilters.tracktrace.exception.ShipmentNotFoundException;
import recommendationSystem.contentFilters.tracktrace.model.Shipment;
import recommendationSystem.contentFilters.tracktrace.model.ShipmentStatus;
import recommendationSystem.contentFilters.tracktrace.model.TrackingEvent;

/**
 * Core domain service that manages shipments and their tracking events using an in-memory store.
 */
@Service
public class ShipmentService {

  private final TrackingNumberGenerator trackingNumberGenerator;
  private final Map<UUID, Shipment> shipmentsById = new ConcurrentHashMap<>();
  private final Map<String, UUID> idsByTrackingNumber = new ConcurrentHashMap<>();

  public ShipmentService(TrackingNumberGenerator trackingNumberGenerator) {
    this.trackingNumberGenerator = trackingNumberGenerator;
  }

  public Shipment createShipment(CreateShipmentRequest request) {
    Objects.requireNonNull(request, "request must not be null");

    Shipment shipment = new Shipment();
    shipment.setId(UUID.randomUUID());
    shipment.setOrigin(request.getOrigin());
    shipment.setDestination(request.getDestination());
    shipment.setDescription(request.getDescription());
    shipment.setReference(request.getReference());
    shipment.setCarrier(request.getCarrier());
    shipment.setServiceLevel(request.getServiceLevel());
    shipment.setRecipient(request.getRecipient());
    shipment.setCustomerName(request.getCustomerName());
    shipment.setExpectedDeliveryDate(request.getExpectedDeliveryDate());

    final Instant now = Instant.now();
    shipment.setCreatedAt(now);
    shipment.setUpdatedAt(now);

    String trackingNumber = normaliseTrackingNumber(request.getTrackingNumber());
    if (trackingNumber == null) {
      trackingNumber = trackingNumberGenerator.nextTrackingNumber();
    }

    registerTrackingNumber(shipment.getId(), trackingNumber);
    shipment.setTrackingNumber(trackingNumber);
    shipment.setStatus(ShipmentStatus.CREATED);

    TrackingEvent creationEvent =
        TrackingEvent.creationEvent(now, request.getOrigin(), request.getDescription());
    shipment.addEvent(creationEvent);

    shipmentsById.put(shipment.getId(), shipment);
    return shipment;
  }

  public Shipment getShipment(UUID shipmentId) {
    Shipment shipment = shipmentsById.get(shipmentId);
    if (shipment == null) {
      throw new ShipmentNotFoundException(shipmentId);
    }
    return shipment;
  }

  public Shipment getShipmentByTrackingNumber(String trackingNumber) {
    String normalised = normaliseTrackingNumber(trackingNumber);
    UUID shipmentId = idsByTrackingNumber.get(normalised);
    if (shipmentId == null) {
      throw new ShipmentNotFoundException(trackingNumber);
    }
    return getShipment(shipmentId);
  }

  public List<Shipment> listShipments(
      Optional<ShipmentStatus> status, Optional<String> origin, Optional<String> destination) {
    return shipmentsById.values().stream()
        .filter(shipment -> status.map(value -> value == shipment.getStatus()).orElse(true))
        .filter(
            shipment ->
                origin
                    .map(value ->
                        shipment.getOrigin() != null
                            && shipment.getOrigin().equalsIgnoreCase(value))
                    .orElse(true))
        .filter(
            shipment ->
                destination
                    .map(value ->
                        shipment.getDestination() != null
                            && shipment.getDestination().equalsIgnoreCase(value))
                    .orElse(true))
        .sorted(Comparator.comparing(Shipment::getUpdatedAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
        .collect(Collectors.toList());
  }

  public TrackingEvent addTrackingEvent(UUID shipmentId, AddTrackingEventRequest request) {
    Shipment shipment = getShipment(shipmentId);
    return addTrackingEvent(shipment, request);
  }

  public TrackingEvent addTrackingEvent(String trackingNumber, AddTrackingEventRequest request) {
    Shipment shipment = getShipmentByTrackingNumber(trackingNumber);
    return addTrackingEvent(shipment, request);
  }

  private TrackingEvent addTrackingEvent(Shipment shipment, AddTrackingEventRequest request) {
    Objects.requireNonNull(request, "request must not be null");

    synchronized (shipment) {
      Instant timestamp = request.getTimestamp() != null ? request.getTimestamp() : Instant.now();
      boolean customerVisible =
          request.getCustomerVisible() == null || Boolean.TRUE.equals(request.getCustomerVisible());
      TrackingEvent event =
          new TrackingEvent(
              UUID.randomUUID(),
              request.getStatus(),
              timestamp,
              request.getLocation(),
              request.getSummary(),
              request.getDetails(),
              request.getReportedBy(),
              customerVisible,
              request.getLatitude(),
              request.getLongitude());

      if (request.getExpectedDeliveryDate() != null) {
        shipment.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
      }

      shipment.addEvent(event);
      return event;
    }
  }

  public List<TrackingEvent> getTrackingHistory(UUID shipmentId) {
    return getShipment(shipmentId).getEvents();
  }

  public List<TrackingEvent> getTrackingHistoryByTrackingNumber(String trackingNumber) {
    return getShipmentByTrackingNumber(trackingNumber).getEvents();
  }

  private void registerTrackingNumber(UUID shipmentId, String trackingNumber) {
    String normalised = normaliseTrackingNumber(trackingNumber);
    UUID previous = idsByTrackingNumber.putIfAbsent(normalised, shipmentId);
    if (previous != null) {
      throw new DuplicateTrackingNumberException(trackingNumber);
    }
  }

  private String normaliseTrackingNumber(String trackingNumber) {
    if (trackingNumber == null) {
      return null;
    }
    String trimmed = trackingNumber.trim();
    return trimmed.isEmpty() ? null : trimmed.toUpperCase();
  }
}
