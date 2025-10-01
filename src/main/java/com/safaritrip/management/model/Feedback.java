package com.safaritrip.management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "feedback")
@Getter
@Setter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "tourist_id", nullable = false)
    private User tourist;

    @Column(nullable = false)
    private int rating; // e.g., 1 to 5

    @Lob // Specifies that this should be stored as a large object
    @Column(columnDefinition = "TEXT")
    private String review;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String adminReply;
}