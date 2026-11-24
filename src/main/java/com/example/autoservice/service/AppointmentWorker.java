package com.example.autoservice.service;

import com.example.autoservice.model.Appointment;
import com.example.autoservice.repository.AppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class AppointmentWorker {

    private static final Logger log = LoggerFactory.getLogger(AppointmentWorker.class);

    private final AppointmentRepository appointmentRepository;

    public AppointmentWorker(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void processPending(int workers, int tasksPerWorker) {
        ExecutorService executor = Executors.newFixedThreadPool(workers);
        for (int i = 0; i < workers; i++) {
            final int workerIndex = i + 1;
            executor.submit(() -> {
                Thread.currentThread().setName("appointment-worker-" + workerIndex);
                log.info("Поток {} начал работу", Thread.currentThread().getName());
                for (int j = 0; j < tasksPerWorker; j++) {
                    Optional<Appointment> opt = appointmentRepository.pollPending();
                    if (opt.isEmpty()) {
                        log.info("Поток {}: очередь пуста", Thread.currentThread().getName());
                        break;
                    }
                    Appointment a = opt.get();
                    a.setStatus(Appointment.Status.IN_PROGRESS);
                    appointmentRepository.save(a);
                    log.info("Поток {} обработал заявку id={} (клиент id={})",
                            Thread.currentThread().getName(), a.getId(), a.getCustomerId());
                }
            });
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }
}