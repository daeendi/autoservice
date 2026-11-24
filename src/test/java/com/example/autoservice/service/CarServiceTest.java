package com.example.autoservice.service;

import com.example.autoservice.model.Car;
import com.example.autoservice.model.CustomerUser;
import com.example.autoservice.model.ManagerUser;
import com.example.autoservice.model.User;
import com.example.autoservice.repository.CarRepository;
import com.example.autoservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

    private UserRepository userRepository;
    private CarRepository carRepository;
    private CarService carService;
    private CustomerUser customer;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        carRepository = new CarRepository();
        carService = new CarService(carRepository, userRepository);
        customer = (CustomerUser) userRepository.save(
                new CustomerUser(null, "Клиент", "c@mail.ru"));
    }

    @Test
    void addCar_shouldSaveWhenValid() {
        Car car = new Car(null, "XTA211500A1234567", "Lada", "Vesta", 2021, customer.getId());
        Car saved = carService.addCar(car);
        assertNotNull(saved.getId());
        assertEquals("XTA211500A1234567", saved.getVin());
    }

    @Test
    void addCar_shouldFailOnBadVin() {
        Car car = new Car(null, "SHORT", "Lada", "Vesta", 2021, customer.getId());
        assertThrows(IllegalArgumentException.class, () -> carService.addCar(car));
    }

    @Test
    void addCar_shouldFailWhenOwnerIsNotCustomer() {
        User manager = userRepository.save(new ManagerUser(null, "M", "m@mail.ru", "dept"));
        Car car = new Car(null, "XTA211500A1234568", "Lada", "Vesta", 2021, manager.getId());
        assertThrows(IllegalStateException.class, () -> carService.addCar(car));
    }
}