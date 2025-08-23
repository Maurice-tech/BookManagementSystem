package com.booksystem.bookManagement.service;

import com.booksystem.bookManagement.entity.Book;
import com.booksystem.bookManagement.entity.Checkout;
import com.booksystem.bookManagement.payload.response.AppResponse;
import com.booksystem.bookManagement.payload.response.BookResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BookService {

    AppResponse<BookResponse> createBook(Book book, List<String> authorNames);
    AppResponse<BookResponse>updateBook(Long bookId, Book updated, List<String> authorNames);
    AppResponse<Page<BookResponse>> search(String title, String isbn, String publisher, LocalDateTime from, LocalDateTime to, Pageable pageable);
    AppResponse<String> uploadCover(Long bookId, MultipartFile file);
    AppResponse<List<Checkout>> listCheckedOutBooks();
    AppResponse<List<Map<String, Object>> >checkedOutInfoForLibrarian();
}
