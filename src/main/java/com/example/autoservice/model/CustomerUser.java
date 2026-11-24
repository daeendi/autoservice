package com.example.autoservice.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerUser extends User {
    private final List<Car> cars = new ArrayList<>();

    public CustomerUser(Long id, String name, String email) {
        super(id, name, email, UserRole.CUSTOMER);
    }

    public List<Car> getCars() { return cars; }
    public void addCar(Car car) { cars.add(car); }

    @Override
    public String getDescription() { return "Клиент, автомобилей: " + cars.size(); }
}