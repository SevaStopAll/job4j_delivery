package ru.job4j.delivery.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.delivery.domain.Delivery;
import ru.job4j.delivery.domain.Status;
import ru.job4j.delivery.service.DeliveryService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {
    private final DeliveryService deliveries;

    @GetMapping("/")
    public List<Delivery> findAll() {
        return (List<Delivery>) this.deliveries.findAll();
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<Delivery> patch(@RequestBody Status status, @PathVariable int id) {
        var delivery = deliveries.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        delivery.setStatus(deliveries.findStatusById(status.getId()).get());
        var rsl = deliveries.update(delivery);
        return new ResponseEntity<>(delivery, rsl ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
