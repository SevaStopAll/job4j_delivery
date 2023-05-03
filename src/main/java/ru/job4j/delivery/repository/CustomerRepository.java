package ru.job4j.delivery.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByName(String name);

    Collection<Customer> findAll();
}
