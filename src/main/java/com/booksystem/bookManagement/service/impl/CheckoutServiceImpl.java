package com.booksystem.bookManagement.service.impl;

import com.booksystem.bookManagement.entity.Book;
import com.booksystem.bookManagement.entity.Checkout;
import com.booksystem.bookManagement.entity.enums.CheckoutStatus;
import com.booksystem.bookManagement.payload.response.AppResponse;
import com.booksystem.bookManagement.payload.response.CheckoutResponse;
import com.booksystem.bookManagement.repository.BookRepository;
import com.booksystem.bookManagement.repository.CheckoutRepository;
import com.booksystem.bookManagement.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final CheckoutRepository checkoutRepository;
    private final BookRepository bookRepository;

    @Transactional
    @Override
    public AppResponse<CheckoutResponse> checkoutBook(Long userId, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));

        if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No copies available");
        }

        // decrement available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Checkout c = Checkout.builder()
                .userId(userId)
                .book(book)
                .checkoutDate(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(10))
                .status(CheckoutStatus.CHECKED_OUT)
                .build();

        Checkout saved = checkoutRepository.save(c);

        CheckoutResponse response = CheckoutResponse.builder()
                .checkoutId(saved.getId())
                .userId(saved.getUserId())
                .book(saved.getBook())
                .checkoutDate(saved.getCheckoutDate())
                .dueDate(saved.getDueDate())
                .checkinDate(saved.getCheckinDate())
                .status(saved.getStatus())
                .build();

        return AppResponse.success("Book checked out successfully", response, HttpStatus.CREATED.value());
    }

    @Transactional
    @Override
    public AppResponse<CheckoutResponse> checkinBook(Long checkoutId) {
        Checkout c = checkoutRepository.findById(checkoutId)
                .orElseThrow(() -> new NoSuchElementException("Checkout not found"));

        if (c.getStatus() != CheckoutStatus.CHECKED_OUT) {
            throw new IllegalStateException("Book not checked out");
        }

        c.setCheckinDate(LocalDateTime.now());
        c.setStatus(CheckoutStatus.RETURNED);

        // increment available copies
        Book book = c.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        Checkout saved = checkoutRepository.save(c);

        CheckoutResponse response = CheckoutResponse.builder()
                .checkoutId(saved.getId())
                .userId(saved.getUserId())
                .book(saved.getBook())
                .checkoutDate(saved.getCheckoutDate())
                .dueDate(saved.getDueDate())
                .checkinDate(saved.getCheckinDate())
                .status(saved.getStatus())
                .build();

        return AppResponse.success("Book checked in successfully", response, HttpStatus.CREATED.value());
    }

}
