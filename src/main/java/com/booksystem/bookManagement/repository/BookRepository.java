package com.booksystem.bookManagement.repository;

import com.booksystem.bookManagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("""
    SELECT b FROM Book b
    WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
      AND (:isbn IS NULL OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :isbn, '%')))
      AND (:publisher IS NULL OR LOWER(b.publisher) LIKE LOWER(CONCAT('%', :publisher, '%')))
""")
    Page<Book> searchBooks(String title, String isbn, String publisher,
                           LocalDateTime from, LocalDateTime to, Pageable pageable);

    boolean existsByIsbn(String isbn);
}

