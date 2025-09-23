package recommendationSystem.contentFilters.tracktrace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import recommendationSystem.contentFilters.tracktrace.dto.AddTrackingEventRequest;
import recommendationSystem.contentFilters.tracktrace.dto.CreateShipmentRequest;
import recommendationSystem.contentFilters.tracktrace.dto.ShipmentResponse;
import recommendationSystem.contentFilters.tracktrace.dto.ShipmentSummaryResponse;
import recommendationSystem.contentFilters.tracktrace.dto.TrackingEventResponse;
import recommendationSystem.contentFilters.tracktrace.model.Shipment;
import recommendationSystem.contentFilters.tracktrace.model.ShipmentStatus;
import recommendationSystem.contentFilters.tracktrace.model.TrackingEvent;
import recommendationSystem.contentFilters.tracktrace.service.ShipmentService;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shipments")
@Validated
public class ShipmentController {
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public ResponseEntity<ShipmentResponse> createShipment(@Valid @RequestBody CreateShipmentRequest request) {
        ShipmentStatus status = request.getStatus() == null ? ShipmentStatus.CREATED : request.getStatus();
        Shipment shipment = shipmentService.createShipment(request.getRecipientName(), request.getDestination(), status);
        return ResponseEntity.ok(new ShipmentResponse(shipment));
    }

    @GetMapping
    public List<ShipmentSummaryResponse> getShipments() {
        return shipmentService.getAllShipments()
                .stream()
                .map(ShipmentSummaryResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{trackingNumber}")
    public ShipmentResponse getShipment(@PathVariable String trackingNumber) {
        return new ShipmentResponse(shipmentService.getShipment(trackingNumber));
    }

    @PostMapping("/{trackingNumber}/events")
    public ResponseEntity<TrackingEventResponse> addTrackingEvent(@PathVariable String trackingNumber,
                                                                  @Valid @RequestBody AddTrackingEventRequest request) {
        Instant eventTime = request.getTimestamp() != null ? request.getTimestamp() : Instant.now();
        TrackingEvent event = shipmentService.addTrackingEvent(trackingNumber, eventTime,
                request.getLocation(), request.getDescription(), request.getStatus());
        return ResponseEntity.ok(new TrackingEventResponse(event));
    }
}
