package ru.job4j.delivery.service;

import ru.job4j.delivery.domain.Delivery;
import ru.job4j.delivery.domain.Status;

import java.util.Collection;
import java.util.Optional;

public interface DeliveryService {

    Collection<Delivery> findAll();

    Optional<Delivery> findById(int id);

    Optional<Status> findStatusById(int statusId);

    boolean update(Delivery delivery);
}
