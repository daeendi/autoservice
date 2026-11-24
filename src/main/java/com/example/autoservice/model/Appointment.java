package com.example.autoservice.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Appointment {
    public enum Status { NEW, IN_PROGRESS, DONE, CANCELLED }

    private Long id;
    private Long customerId;
    private Long carId;
    private Long serviceItemId;
    private LocalDateTime scheduledAt;
    private Status status;

    public Appointment(Long id, Long customerId, Long carId, Long serviceItemId,
                       LocalDateTime scheduledAt, Status status) {
        this.id = id;
        this.customerId = customerId;
        this.carId = carId;
        this.serviceItemId = serviceItemId;
        this.scheduledAt = scheduledAt;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }
    public Long getServiceItemId() { return serviceItemId; }
    public void setServiceItemId(Long serviceItemId) { this.serviceItemId = serviceItemId; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return String.format("Appointment{id=%d, customerId=%d, carId=%d, serviceItemId=%d, at=%s, status=%s}",
                id, customerId, carId, serviceItemId, scheduledAt, status);
    }
}