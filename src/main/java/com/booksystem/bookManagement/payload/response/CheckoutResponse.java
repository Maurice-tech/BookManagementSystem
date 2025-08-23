package com.booksystem.bookManagement.payload.response;

import com.booksystem.bookManagement.entity.Book;
import com.booksystem.bookManagement.entity.enums.CheckoutStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CheckoutResponse {
    private Long checkoutId;
    private Long userId;
    private Book book;
    private LocalDateTime checkoutDate;
    private LocalDateTime dueDate;
    private LocalDateTime checkinDate;
    private CheckoutStatus status;
}
