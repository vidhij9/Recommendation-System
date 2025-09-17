package recommendationSystem.contentFilters.tracktrace.model;

/**
 * Represents the lifecycle state of a shipment. The states are intentionally coarse grained so that
 * they can work for parcel, freight or asset tracking scenarios without requiring a backing
 * database.
 */
public enum ShipmentStatus {
  CREATED,
  AWAITING_PICKUP,
  IN_TRANSIT,
  DELAYED,
  OUT_FOR_DELIVERY,
  DELIVERED,
  EXCEPTION,
  CANCELLED
}
