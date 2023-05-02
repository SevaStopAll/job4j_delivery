package ru.job4j.delivery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.delivery.repository.CustomerRepository;
import ru.job4j.delivery.repository.DeliveryRepository;
import ru.job4j.delivery.repository.StatusRepository;
import ru.job4j.delivery.service.DeliveryService;

@Service
@Slf4j
@AllArgsConstructor
public class SimpleDeliveryService implements DeliveryService {
    private final DeliveryRepository deliveries;
    private final CustomerRepository customers;
    private final StatusRepository statuses;

}
