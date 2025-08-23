package com.booksystem.bookManagement.payload.request;

import lombok.Data;

@Data
public class CheckoutRequest {
    private Long userId;
    private Long bookId;
}
