package com.example.autoservice;

import com.example.autoservice.model.AdminUser;
import com.example.autoservice.model.Appointment;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.CarServiceItem;
import com.example.autoservice.model.CustomerUser;
import com.example.autoservice.model.ManagerUser;
import com.example.autoservice.service.AppointmentService;
import com.example.autoservice.service.AppointmentWorker;
import com.example.autoservice.service.CarService;
import com.example.autoservice.service.ServiceCatalogService;
import com.example.autoservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
public class AutoserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoserviceApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserService userService,
                                  CarService carService,
                                  ServiceCatalogService catalogService,
                                  AppointmentService appointmentService,
                                  AppointmentWorker worker) {
        return args -> {
            // 1. Создаем пользователей разных ролей
            AdminUser admin = (AdminUser) userService.register(
                    new AdminUser(null, "Иван Админов", "admin@autoservice.ru", "FULL"));

            ManagerUser manager = (ManagerUser) userService.register(
                    new ManagerUser(null, "Пётр Менеджеров", "manager@autoservice.ru", "Ремонтный цех"));

            CustomerUser customer = (CustomerUser) userService.register(
                    new CustomerUser(null, "Сидор Клиентов", "customer@mail.ru"));

            // 2. Добавляем автомобиль клиенту
            Car car = carService.addCar(new Car(null, "XTA211500A1234567",
                    "Lada", "Vesta", 2021, customer.getId()));

            // 3. Добавляем услуги в каталог
            CarServiceItem oilChange = catalogService.addService(
                    new CarServiceItem(null, "Замена масла", "Замена моторного масла",
                            new BigDecimal("2500.00"), 45));

            CarServiceItem diagnostics = catalogService.addService(
                    new CarServiceItem(null, "Диагностика", "Компьютерная диагностика",
                            new BigDecimal("1500.00"), 30));

            // 4. Создаем заявки на будущее время
            Appointment a1 = appointmentService.create(customer.getId(), car.getId(),
                    oilChange.getId(), LocalDateTime.now().plusDays(1));

            Appointment a2 = appointmentService.create(customer.getId(), car.getId(),
                    diagnostics.getId(), LocalDateTime.now().plusDays(2));

            // 5. Выводим информацию в консоль
            System.out.println("=== ПОЛЬЗОВАТЕЛИ ===");
            System.out.println(userService.findAll());
            System.out.println("=== АВТОМОБИЛИ КЛИЕНТА ===");
            System.out.println(carService.findByOwner(customer.getId()));
            System.out.println("=== КАТАЛОГ УСЛУГ ===");
            System.out.println(catalogService.findAll());
            System.out.println("=== ЗАЯВКИ ===");
            System.out.println(appointmentService.findAll());

            // 6. Многопоточная обработка (демонстрация)
            System.out.println("=== ЗАПУСК МНОГОПОТОЧНОЙ ОБРАБОТКИ ===");
            worker.processPending(3, 5);
        };
    }
}