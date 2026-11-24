package com.example.autoservice.service;

import com.example.autoservice.model.Appointment;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.CarServiceItem;
import com.example.autoservice.model.CustomerUser;
import com.example.autoservice.model.User;
import com.example.autoservice.repository.AppointmentRepository;
import com.example.autoservice.repository.CarRepository;
import com.example.autoservice.repository.ServiceRepository;
import com.example.autoservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final ServiceRepository serviceRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserRepository userRepository,
                              CarRepository carRepository,
                              ServiceRepository serviceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.serviceRepository = serviceRepository;
    }

    public Appointment create(Long customerId, Long carId, Long serviceItemId, LocalDateTime when) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Клиент не найден"));
        if (!(customer instanceof CustomerUser)) {
            throw new IllegalStateException("Заявку может создавать только клиент");
        }
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Автомобиль не найден"));
        if (!car.getOwnerId().equals(customerId)) {
            throw new IllegalStateException("Автомобиль не принадлежит клиенту");
        }
        CarServiceItem item = serviceRepository.findById(serviceItemId)
                .orElseThrow(() -> new IllegalArgumentException("Услуга не найдена"));
        if (when.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Нельзя записаться на прошедшее время");
        }
        Appointment appointment = new Appointment(null, customerId, carId, item.getId(),
                when, Appointment.Status.NEW);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> findByCustomer(Long customerId) {
        return appointmentRepository.findByCustomerId(customerId);
    }

    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    public boolean cancel(Long id) {
        Optional<Appointment> opt = appointmentRepository.findById(id);
        if (opt.isEmpty()) {
            return false;
        }
        Appointment a = opt.get();
        a.setStatus(Appointment.Status.CANCELLED);
        appointmentRepository.save(a);
        return true;
    }
}