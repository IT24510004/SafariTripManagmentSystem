package com.safaritrip.management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehicles") // This line is very important
@Getter
@Setter
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make; // e.g., Toyota

    @Column(nullable = false)
    private String model; // e.g., Hilux

    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private int capacity;
}