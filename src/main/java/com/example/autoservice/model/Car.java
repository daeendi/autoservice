package com.example.autoservice.model;

import java.util.Objects;

public class Car {
    private Long id;
    private String vin;
    private String brand;
    private String model;
    private int year;
    private Long ownerId;

    public Car(Long id, String vin, String brand, String model, int year, Long ownerId) {
        this.id = id;
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.ownerId = ownerId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return String.format("Car{id=%d, vin='%s', %s %s, year=%d, ownerId=%d}",
                id, vin, brand, model, year, ownerId);
    }
}