package com.example.autoservice.model;

import java.math.BigDecimal;
import java.util.Objects;

public class CarServiceItem {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int durationMinutes;

    public CarServiceItem(Long id, String name, String description, BigDecimal price, int durationMinutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationMinutes = durationMinutes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarServiceItem that = (CarServiceItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return String.format("Service{id=%d, name='%s', price=%s, duration=%d min}",
                id, name, price, durationMinutes);
    }
}