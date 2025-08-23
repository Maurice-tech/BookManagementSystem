package com.booksystem.bookManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "checkouts")
public class Checkout {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // assume BookUsers entity exists for readers
    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime checkoutDate;
    private LocalDateTime dueDate;
    private LocalDateTime checkinDate;

    @Enumerated(EnumType.STRING)
    private CheckoutStatus status;
}

