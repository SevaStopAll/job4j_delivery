package ru.job4j.delivery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.delivery.domain.Delivery;
import ru.job4j.delivery.repository.CustomerRepository;
import ru.job4j.delivery.repository.DeliveryRepository;
import ru.job4j.delivery.repository.StatusRepository;
import ru.job4j.delivery.service.DeliveryService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SimpleDeliveryService implements DeliveryService {
    private final DeliveryRepository deliveries;
    private final CustomerRepository customers;
    private final StatusRepository statuses;

    @KafkaListener(topics = "delivery_service")
    public void receiveStatus(Map<String, Integer> data) {
        Delivery delivery = new Delivery();
        data.put("address", orderToDeliver.getCustomer().getAddress());
        data.put("dishes", orderToDeliver.getDishes().stream().map(dish -> dish.getId()).collect(Collectors.toList()));
        data.put("time", LocalDateTime.now());
        data.put("price", orderToDeliver.getPrice());
        data.put("payment_method", orderToDeliver.getMethod().getName());

        String dishes = order.get("dishes").toString();
        kitchenOrder.setDescription(dishes);
        kitchenOrder.setStatus(new Status(Integer.parseInt(order.get("status").toString()), "Создан"));
        log.debug(dishes);
        log.debug(order.get("time").toString());
        orders.save(kitchenOrder);
    }

    @Transactional
    public void changeStatus(Order order) {
        Map<String, Integer> data = new HashMap();
        data.put("id", order.getId());
        data.put("status", order.getStatus().getId());
        kafkaTemplate.send("cooked_order", data);
    }

}
