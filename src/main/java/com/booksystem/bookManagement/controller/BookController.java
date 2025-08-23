package com.booksystem.bookManagement.controller;
import com.booksystem.bookManagement.entity.Book;
import com.booksystem.bookManagement.entity.Checkout;
import com.booksystem.bookManagement.payload.response.AppResponse;
import com.booksystem.bookManagement.payload.response.BookResponse;
import com.booksystem.bookManagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<AppResponse<BookResponse>> createBook(
            @RequestPart("book") Book book,
            @RequestPart(value = "authors", required = false) List<String> authorNames) {

        AppResponse<BookResponse> response = bookService.createBook(book, authorNames);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<AppResponse<BookResponse>> updateBook(
            @PathVariable Long bookId,
            @RequestPart("book") Book updated,
            @RequestPart(value = "authors", required = false) List<String> authorNames) {

        AppResponse<BookResponse> response = bookService.updateBook(bookId, updated, authorNames);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/{bookId}/cover")
    public ResponseEntity<AppResponse<String>> uploadCover(
            @PathVariable Long bookId,
            @RequestParam("file") MultipartFile file) {

        AppResponse<String> response = bookService.uploadCover(bookId, file);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<AppResponse<Page<BookResponse>>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            Pageable pageable) {

        AppResponse<Page<BookResponse>> response =
                bookService.search(title, isbn, publisher, from, to, pageable);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/checked-out")
    public ResponseEntity<AppResponse<List<Checkout>>> listCheckedOutBooks() {
        AppResponse<List<Checkout>> response = bookService.listCheckedOutBooks();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/checked-out/info")
    public ResponseEntity<AppResponse<List<Map<String, Object>>>> checkedOutInfoForLibrarian() {
        AppResponse<List<Map<String, Object>>> response = bookService.checkedOutInfoForLibrarian();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
