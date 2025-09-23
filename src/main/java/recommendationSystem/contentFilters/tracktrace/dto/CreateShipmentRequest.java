package recommendationSystem.contentFilters.tracktrace.dto;

import recommendationSystem.contentFilters.tracktrace.model.ShipmentStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateShipmentRequest {
    @NotBlank(message = "Recipient name is required")
    @Size(max = 100, message = "Recipient name must be at most 100 characters")
    private String recipientName;

    @NotBlank(message = "Destination is required")
    @Size(max = 150, message = "Destination must be at most 150 characters")
    private String destination;

    private ShipmentStatus status = ShipmentStatus.CREATED;

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }
}
