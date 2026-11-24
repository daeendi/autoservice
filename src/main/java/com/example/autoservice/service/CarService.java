package com.example.autoservice.service;

import com.example.autoservice.model.Car;
import com.example.autoservice.model.User;
import com.example.autoservice.model.UserRole;
import com.example.autoservice.repository.CarRepository;
import com.example.autoservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public CarService(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    public Car addCar(Car car) {
        User owner = userRepository.findById(car.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Владелец не найден"));
        if (owner.getRole() != UserRole.CUSTOMER) {
            throw new IllegalStateException("Автомобиль может принадлежать только клиенту");
        }
        if (car.getVin() == null || car.getVin().length() != 17) {
            throw new IllegalArgumentException("VIN должен содержать 17 символов");
        }
        if (carRepository.findByVin(car.getVin()).isPresent()) {
            throw new IllegalStateException("Автомобиль с таким VIN уже существует");
        }
        return carRepository.save(car);
    }

    public List<Car> findByOwner(Long ownerId) {
        return carRepository.findByOwnerId(ownerId);
    }

    public Optional<Car> findByVin(String vin) {
        return carRepository.findByVin(vin);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }
}