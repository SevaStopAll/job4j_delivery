package ru.job4j.delivery.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.delivery.domain.Status;

import java.util.Collection;

public interface StatusRepository extends CrudRepository<Status,Integer> {
    Collection<Status> findAll();
}
