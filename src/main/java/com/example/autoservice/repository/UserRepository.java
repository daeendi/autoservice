package com.example.autoservice.repository;

import com.example.autoservice.model.User;
import com.example.autoservice.model.UserRole;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private final Map<Long, User> storage = new HashMap<>();
    private long nextId = 1L;

    public synchronized User save(User user) {
        if (user.getId() == null) {
            user.setId(nextId++);
        }
        storage.put(user.getId(), user);
        return user;
    }

    public synchronized Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public synchronized List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    public synchronized List<User> findByRole(UserRole role) {
        return storage.values().stream()
                .filter(u -> u.getRole() == role)
                .collect(Collectors.toList());
    }

    public synchronized Optional<User> findByEmail(String email) {
        return storage.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public synchronized boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }

    public synchronized int size() {
        return storage.size();
    }
}