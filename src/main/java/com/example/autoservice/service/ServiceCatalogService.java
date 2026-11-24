package com.example.autoservice.service;

import com.example.autoservice.model.CarServiceItem;
import com.example.autoservice.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServiceCatalogService {

    private final ServiceRepository serviceRepository;

    public ServiceCatalogService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public CarServiceItem addService(CarServiceItem item) {
        if (item.getPrice() == null || item.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной");
        }
        return serviceRepository.save(item);
    }

    public List<CarServiceItem> findAll() {
        return serviceRepository.findAll();
    }

    public CarServiceItem findById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Услуга не найдена: " + id));
    }
}