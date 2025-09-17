package recommendationSystem.contentFilters.tracktrace.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recommendationSystem.contentFilters.tracktrace.dto.AddTrackingEventRequest;
import recommendationSystem.contentFilters.tracktrace.dto.CreateShipmentRequest;
import recommendationSystem.contentFilters.tracktrace.exception.DuplicateTrackingNumberException;
import recommendationSystem.contentFilters.tracktrace.model.Shipment;
import recommendationSystem.contentFilters.tracktrace.model.ShipmentStatus;
import recommendationSystem.contentFilters.tracktrace.model.TrackingEvent;

class ShipmentServiceTest {

  private ShipmentService shipmentService;

  @BeforeEach
  void setUp() {
    shipmentService = new ShipmentService(new TrackingNumberGenerator());
  }

  @Test
  void createShipmentInitialisesTrackingNumberAndEvent() {
    CreateShipmentRequest request = new CreateShipmentRequest();
    request.setOrigin("Berlin");
    request.setDestination("Paris");
    request.setDescription("Initial booking");

    Shipment shipment = shipmentService.createShipment(request);

    assertNotNull(shipment.getId());
    assertNotNull(shipment.getTrackingNumber());
    assertFalse(shipment.getTrackingNumber().isEmpty());
    assertEquals(ShipmentStatus.CREATED, shipment.getStatus());
    assertEquals(1, shipment.getEvents().size());
    TrackingEvent creationEvent = shipment.getEvents().get(0);
    assertEquals(ShipmentStatus.CREATED, creationEvent.getStatus());
    assertEquals("Berlin", creationEvent.getLocation());
    assertEquals("Initial booking", creationEvent.getSummary());
  }

  @Test
  void addTrackingEventUpdatesShipmentStatusAndDeliveryDate() {
    CreateShipmentRequest request = new CreateShipmentRequest();
    request.setOrigin("Berlin");
    request.setDestination("Paris");
    Shipment shipment = shipmentService.createShipment(request);

    AddTrackingEventRequest inTransit = new AddTrackingEventRequest();
    inTransit.setStatus(ShipmentStatus.IN_TRANSIT);
    inTransit.setLocation("Berlin Hub");
    inTransit.setSummary("Departed facility");
    LocalDate eta = LocalDate.now().plusDays(2);
    inTransit.setExpectedDeliveryDate(eta);

    TrackingEvent event = shipmentService.addTrackingEvent(shipment.getId(), inTransit);

    assertEquals(ShipmentStatus.IN_TRANSIT, shipment.getStatus());
    assertEquals(eta, shipment.getExpectedDeliveryDate());
    assertEquals(event.getId(), shipment.getEvents().get(1).getId());
  }

  @Test
  void listShipmentsFiltersByStatus() {
    CreateShipmentRequest toDeliver = new CreateShipmentRequest();
    toDeliver.setOrigin("Berlin");
    toDeliver.setDestination("Paris");
    Shipment created = shipmentService.createShipment(toDeliver);

    CreateShipmentRequest deliveredRequest = new CreateShipmentRequest();
    deliveredRequest.setOrigin("Berlin");
    deliveredRequest.setDestination("Rome");
    Shipment delivered = shipmentService.createShipment(deliveredRequest);

    AddTrackingEventRequest deliveredEvent = new AddTrackingEventRequest();
    deliveredEvent.setStatus(ShipmentStatus.DELIVERED);
    deliveredEvent.setLocation("Rome");
    deliveredEvent.setSummary("Delivered to consignee");
    shipmentService.addTrackingEvent(delivered.getId(), deliveredEvent);

    List<Shipment> deliveredShipments =
        shipmentService.listShipments(Optional.of(ShipmentStatus.DELIVERED), Optional.empty(), Optional.empty());

    assertEquals(1, deliveredShipments.size());
    assertEquals(delivered.getId(), deliveredShipments.get(0).getId());
    assertEquals(ShipmentStatus.CREATED, created.getStatus());
    assertEquals(ShipmentStatus.DELIVERED, deliveredShipments.get(0).getStatus());
  }

  @Test
  void creatingShipmentWithDuplicateTrackingNumberFails() {
    CreateShipmentRequest request = new CreateShipmentRequest();
    request.setOrigin("Berlin");
    request.setDestination("Paris");
    request.setTrackingNumber("Custom-123");
    shipmentService.createShipment(request);

    CreateShipmentRequest duplicateRequest = new CreateShipmentRequest();
    duplicateRequest.setOrigin("Berlin");
    duplicateRequest.setDestination("Paris");
    duplicateRequest.setTrackingNumber("custom-123");

    assertThrows(
        DuplicateTrackingNumberException.class,
        () -> shipmentService.createShipment(duplicateRequest));
  }
}
