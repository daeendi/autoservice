package com.example.autoservice.repository;

import com.example.autoservice.model.Appointment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class AppointmentRepository {

    private final Map<Long, Appointment> storage = new ConcurrentHashMap<>();
    private final Queue<Appointment> pendingQueue = new ConcurrentLinkedQueue<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public Appointment save(Appointment appointment) {
        if (appointment.getId() == null) {
            appointment.setId(nextId.getAndIncrement());
        }
        storage.put(appointment.getId(), appointment);
        if (appointment.getStatus() == Appointment.Status.NEW) {
            pendingQueue.offer(appointment);
        }
        return appointment;
    }

    public Optional<Appointment> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Appointment> findAll() {
        return new ArrayList<>(storage.values());
    }

    public List<Appointment> findByCustomerId(Long customerId) {
        return storage.values().stream()
                .filter(a -> a.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    public List<Appointment> findByStatus(Appointment.Status status) {
        return storage.values().stream()
                .filter(a -> a.getStatus() == status)
                .collect(Collectors.toList());
    }

    public Optional<Appointment> pollPending() {
        return Optional.ofNullable(pendingQueue.poll());
    }

    public int pendingSize() {
        return pendingQueue.size();
    }

    public boolean deleteById(Long id) {
        Appointment removed = storage.remove(id);
        if (removed != null) {
            pendingQueue.remove(removed);
            return true;
        }
        return false;
    }
}