package com.booksystem.bookManagement.repository;

import com.booksystem.bookManagement.entity.Checkout;
import com.booksystem.bookManagement.entity.enums.CheckoutStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    List<Checkout> findByStatus(CheckoutStatus status);
    List<Checkout> findByBookIdAndStatus(Long bookId, CheckoutStatus status);
    List<Checkout> findByUserIdAndStatus(Long userId, CheckoutStatus status);
    List<Checkout> findByStatusAndDueDateBetween(CheckoutStatus status, LocalDateTime from, LocalDateTime to);
    List<Checkout> findByStatusAndDueDateBefore(CheckoutStatus status, LocalDateTime before);

    List<Checkout> findByReturnDateIsNull();
}

