package com.example.autoservice.repository;

import com.example.autoservice.model.CarServiceItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ServiceRepository {

    private final Map<Long, CarServiceItem> storage = new HashMap<>();
    private long nextId = 1L;

    public synchronized CarServiceItem save(CarServiceItem item) {
        if (item.getId() == null) {
            item.setId(nextId++);
        }
        storage.put(item.getId(), item);
        return item;
    }

    public synchronized Optional<CarServiceItem> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public synchronized Optional<CarServiceItem> findByName(String name) {
        return storage.values().stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public synchronized List<CarServiceItem> findAll() {
        return new ArrayList<>(storage.values());
    }

    public synchronized boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }
}
