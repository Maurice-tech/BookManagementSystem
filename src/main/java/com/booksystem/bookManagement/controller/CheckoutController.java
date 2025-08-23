package com.booksystem.bookManagement.controller;

import com.booksystem.bookManagement.payload.request.CheckoutRequest;
import com.booksystem.bookManagement.payload.response.AppResponse;
import com.booksystem.bookManagement.payload.response.CheckoutResponse;
import com.booksystem.bookManagement.service.BookService;
import com.booksystem.bookManagement.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<AppResponse<CheckoutResponse>> checkoutBook(@RequestBody CheckoutRequest request) {
        return ResponseEntity.ok(checkoutService.checkoutBook(request.getUserId(), request.getBookId()));
    }

    @PostMapping("/{checkoutId}/checkin")
    public ResponseEntity<AppResponse<CheckoutResponse>> checkinBook(@PathVariable Long checkoutId) {
        return ResponseEntity.ok(checkoutService.checkinBook(checkoutId));
    }
}

