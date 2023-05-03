package ru.job4j.delivery.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private int id;

    @Setter
    @Getter
    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status")
    @Setter
    @Getter
    private Status status;
}
