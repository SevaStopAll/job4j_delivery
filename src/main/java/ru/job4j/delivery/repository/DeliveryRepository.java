package ru.job4j.delivery.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.delivery.domain.Delivery;

import java.util.Collection;

public interface DeliveryRepository extends CrudRepository<Delivery, Integer> {
    Collection<Delivery> findAll();
}
