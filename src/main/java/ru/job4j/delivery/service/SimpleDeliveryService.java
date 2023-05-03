package ru.job4j.delivery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.delivery.domain.Delivery;
import ru.job4j.delivery.domain.Status;
import ru.job4j.delivery.repository.DeliveryRepository;
import ru.job4j.delivery.repository.StatusRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class SimpleDeliveryService implements DeliveryService {
    private final DeliveryRepository deliveries;
    private final StatusRepository statuses;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "delivery_service")
    public void receiveStatus(Map data) {
        Delivery delivery = new Delivery();
        delivery.setAddress((String) data.get("address"));
        delivery.setStatus(statuses.findById(1).get());
        delivery.setPrice((Integer) data.get("price"));
        delivery.setPaymentMethod((String) data.get("payment_method"));
        log.debug(delivery.getAddress());
        deliveries.save(delivery);
    }

    @Transactional
    public void changeStatus(Delivery delivery) {
        Map data = new HashMap();
        data.put("id", delivery.getId());
        data.put("status", delivery.getStatus().getId());
        kafkaTemplate.send("delivired_order", data);
    }

    @Override
    public Collection<Delivery> findAll() {
        return deliveries.findAll();
    }

    @Override
    public Optional<Delivery> findById(int id) {
        return deliveries.findById(id);
    }

    @Override
    public Optional<Status> findStatusById(int statusId) {
        return Optional.of(statuses.findById(statusId).get());
    }

    @Override
    public boolean update(Delivery delivery) {
        if (deliveries.findById(delivery.getId()).isEmpty()) {
            return false;
        }
        deliveries.save(delivery);
        changeStatus(delivery);
        return true;
    }

}
