package recommendationSystem.contentFilters.tracktrace.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import recommendationSystem.contentFilters.tracktrace.dto.AddTrackingEventRequest;
import recommendationSystem.contentFilters.tracktrace.dto.CreateShipmentRequest;
import recommendationSystem.contentFilters.tracktrace.dto.ShipmentResponse;
import recommendationSystem.contentFilters.tracktrace.dto.ShipmentSummaryResponse;
import recommendationSystem.contentFilters.tracktrace.dto.TrackingEventResponse;
import recommendationSystem.contentFilters.tracktrace.model.Shipment;
import recommendationSystem.contentFilters.tracktrace.model.ShipmentStatus;
import recommendationSystem.contentFilters.tracktrace.model.TrackingEvent;
import recommendationSystem.contentFilters.tracktrace.service.ShipmentService;

/**
 * REST entrypoint exposing the track and trace functionality.
 */
@RestController
@RequestMapping("/api/shipments")
@Validated
public class ShipmentController {

  private final ShipmentService shipmentService;

  public ShipmentController(ShipmentService shipmentService) {
    this.shipmentService = shipmentService;
  }

  @PostMapping
  public ShipmentResponse createShipment(@Valid @RequestBody CreateShipmentRequest request) {
    Shipment shipment = shipmentService.createShipment(request);
    return toShipmentResponse(shipment);
  }

  @GetMapping
  public List<ShipmentSummaryResponse> listShipments(
      @RequestParam(value = "status", required = false) ShipmentStatus status,
      @RequestParam(value = "origin", required = false) String origin,
      @RequestParam(value = "destination", required = false) String destination) {
    return shipmentService
        .listShipments(Optional.ofNullable(status), Optional.ofNullable(origin), Optional.ofNullable(destination))
        .stream()
        .map(this::toShipmentSummaryResponse)
        .collect(Collectors.toList());
  }

  @GetMapping("/{shipmentId}")
  public ShipmentResponse getShipment(@PathVariable UUID shipmentId) {
    return toShipmentResponse(shipmentService.getShipment(shipmentId));
  }

  @GetMapping("/tracking/{trackingNumber}")
  public ShipmentResponse getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
    return toShipmentResponse(shipmentService.getShipmentByTrackingNumber(trackingNumber));
  }

  @PostMapping("/{shipmentId}/events")
  public TrackingEventResponse addEvent(
      @PathVariable UUID shipmentId, @Valid @RequestBody AddTrackingEventRequest request) {
    TrackingEvent event = shipmentService.addTrackingEvent(shipmentId, request);
    return toTrackingEventResponse(event);
  }

  @PostMapping("/tracking/{trackingNumber}/events")
  public TrackingEventResponse addEventByTrackingNumber(
      @PathVariable String trackingNumber, @Valid @RequestBody AddTrackingEventRequest request) {
    TrackingEvent event = shipmentService.addTrackingEvent(trackingNumber, request);
    return toTrackingEventResponse(event);
  }

  @GetMapping("/{shipmentId}/events")
  public List<TrackingEventResponse> getTrackingHistory(@PathVariable UUID shipmentId) {
    return shipmentService.getTrackingHistory(shipmentId).stream()
        .map(this::toTrackingEventResponse)
        .collect(Collectors.toList());
  }

  @GetMapping("/tracking/{trackingNumber}/events")
  public List<TrackingEventResponse> getTrackingHistoryByTrackingNumber(
      @PathVariable String trackingNumber) {
    return shipmentService.getTrackingHistoryByTrackingNumber(trackingNumber).stream()
        .map(this::toTrackingEventResponse)
        .collect(Collectors.toList());
  }

  private ShipmentResponse toShipmentResponse(Shipment shipment) {
    List<TrackingEventResponse> events =
        shipment.getEvents().stream().map(this::toTrackingEventResponse).collect(Collectors.toList());

    return new ShipmentResponse(
        shipment.getId(),
        shipment.getTrackingNumber(),
        shipment.getReference(),
        shipment.getOrigin(),
        shipment.getDestination(),
        shipment.getCarrier(),
        shipment.getServiceLevel(),
        shipment.getRecipient(),
        shipment.getDescription(),
        shipment.getCustomerName(),
        shipment.getStatus(),
        shipment.getExpectedDeliveryDate(),
        shipment.getCreatedAt(),
        shipment.getUpdatedAt(),
        events);
  }

  private ShipmentSummaryResponse toShipmentSummaryResponse(Shipment shipment) {
    return new ShipmentSummaryResponse(
        shipment.getId(),
        shipment.getTrackingNumber(),
        shipment.getOrigin(),
        shipment.getDestination(),
        shipment.getStatus(),
        shipment.getExpectedDeliveryDate(),
        shipment.getUpdatedAt(),
        shipment.getCarrier(),
        shipment.getServiceLevel());
  }

  private TrackingEventResponse toTrackingEventResponse(TrackingEvent event) {
    return new TrackingEventResponse(
        event.getId(),
        event.getStatus(),
        event.getTimestamp(),
        event.getLocation(),
        event.getSummary(),
        event.getDetails(),
        event.getReportedBy(),
        event.isCustomerVisible(),
        event.getLatitude(),
        event.getLongitude());
  }
}
