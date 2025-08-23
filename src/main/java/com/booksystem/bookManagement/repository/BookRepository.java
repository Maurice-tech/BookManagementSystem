package com.booksystem.bookManagement.repository;

import com.booksystem.bookManagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Book> findByIsbnContaining(String isbn, Pageable pageable);
    Page<Book> findByPublisherContainingIgnoreCase(String publisher, Pageable pageable);
    Page<Book> findByDateAddedBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
}

