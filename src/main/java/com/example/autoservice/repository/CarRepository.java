package com.example.autoservice.repository;

import com.example.autoservice.model.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CarRepository {

    private final Map<Long, Car> storage = new HashMap<>();
    private long nextId = 1L;

    public synchronized Car save(Car car) {
        if (car.getId() == null) {
            car.setId(nextId++);
        }
        storage.put(car.getId(), car);
        return car;
    }

    public synchronized Optional<Car> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public synchronized Optional<Car> findByVin(String vin) {
        return storage.values().stream()
                .filter(c -> c.getVin().equalsIgnoreCase(vin))
                .findFirst();
    }

    public synchronized List<Car> findByOwnerId(Long ownerId) {
        return storage.values().stream()
                .filter(c -> c.getOwnerId().equals(ownerId))
                .collect(Collectors.toList());
    }

    public synchronized List<Car> findAll() {
        return new ArrayList<>(storage.values());
    }

    public synchronized boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }
}