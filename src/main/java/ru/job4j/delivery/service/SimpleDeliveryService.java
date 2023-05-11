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

    /**
     * Создать новую доставку.
     *
     * @param data данные о заказе.
     */
    @KafkaListener(topics = "delivery_service")
    public void save(Map data) {
        Delivery delivery = new Delivery();
        delivery.setAddress((String) data.get("address"));
        delivery.setStatus(statuses.findById(1).get());
        delivery.setPrice((Integer) data.get("price"));
        delivery.setPaymentMethod((String) data.get("payment_method"));
        log.debug(delivery.getAddress());
        deliveries.save(delivery);
    }

    /**
     * Изменить статус доставки и отправить новый статус в главный сервис .
     *
     * @param delivery данные о доставке.
     */
    @Transactional
    public void changeStatus(Delivery delivery) {
        Map data = new HashMap();
        data.put("id", delivery.getId());
        data.put("status", delivery.getStatus().getId());
        kafkaTemplate.send("delivered_order", data);
    }

    /**
     * Изменить статус доставки и отправить новый статус в главный сервис .
     *
     * @return  список доставляемых и доставленных заказов.
     */
    @Override
    public Collection<Delivery> findAll() {
        return deliveries.findAll();
    }

    /**
     * Найти доставку по идентификатору.
     *
     * @param id идентификатор.
     * @return Optional доставки.
     */

    @Override
    public Optional<Delivery> findById(int id) {
        return deliveries.findById(id);
    }

    /**
     * Найти статус по его идентификатору.
     *
     * @param statusId идентификатор.
     * @return Optional статуса доставки.
     */
    @Override
    public Optional<Status> findStatusById(int statusId) {
        return Optional.of(statuses.findById(statusId).get());
    }

    /**
     * Обновить доставку.
     *
     * @param delivery доставка.
     * @return результат обновления.
     */
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
