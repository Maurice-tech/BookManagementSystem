package com.booksystem.bookManagement.service;

import com.booksystem.bookManagement.payload.response.AppResponse;
import com.booksystem.bookManagement.payload.response.CheckoutResponse;
import org.springframework.transaction.annotation.Transactional;

public interface CheckoutService {
    @Transactional
    AppResponse<CheckoutResponse> checkoutBook(Long userId, Long bookId);

    @Transactional
    AppResponse<CheckoutResponse> checkinBook(Long checkoutId);
}
