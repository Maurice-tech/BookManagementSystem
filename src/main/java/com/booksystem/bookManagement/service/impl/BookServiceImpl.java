package com.booksystem.bookManagement.service.impl;

import com.booksystem.bookManagement.entity.Author;
import com.booksystem.bookManagement.entity.Book;
import com.booksystem.bookManagement.entity.BookUsers;
import com.booksystem.bookManagement.entity.Checkout;
import com.booksystem.bookManagement.payload.response.AppResponse;
import com.booksystem.bookManagement.payload.response.BookResponse;
import com.booksystem.bookManagement.repository.AuthorRepository;
import com.booksystem.bookManagement.repository.BookRepository;
import com.booksystem.bookManagement.repository.BookUsersRepository;
import com.booksystem.bookManagement.repository.CheckoutRepository;
import com.booksystem.bookManagement.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CheckoutRepository checkoutRepository;
    private final BookUsersRepository usersRepository;

    @Value("${book.upload-dir}")
    private String uploadDir;

    @Transactional
    @Override
    public AppResponse<BookResponse> createBook(Book book, List<String> authorNames) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        BookUsers user = usersRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Author> authors = authorNames.stream()
                .map(name -> authorRepository.findByName(name)
                        .orElseGet(() -> authorRepository.save(new Author(name))))
                .collect(Collectors.toSet());
        book.setAuthors(authors);
        book.setDateAdded(LocalDateTime.now());
        book.setAvailableCopies(book.getTotalCopies());
        book.setCreatedBy(user);

        Book saved = bookRepository.save(book);
        return AppResponse.success("Book created successfully", mapToResponse(saved), HttpStatus.CREATED.value());
    }


    @Transactional
    @Override
    public AppResponse<BookResponse> updateBook(Long bookId, Book updated, List<String> authorNames) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        BookUsers user = usersRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book existing = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        existing.setTitle(updated.getTitle());
        existing.setIsbn(updated.getIsbn());
        existing.setPublisher(updated.getPublisher());
        existing.setRevision(updated.getRevision());
        existing.setGenre(updated.getGenre());
        existing.setPublishedDate(updated.getPublishedDate());
        existing.setDescription(updated.getDescription());
        existing.setTotalCopies(updated.getTotalCopies());
        existing.setAvailableCopies(updated.getAvailableCopies());

        Set<Author> authors = authorNames.stream()
                .map(name -> authorRepository.findByName(name)
                        .orElseGet(() -> authorRepository.save(new Author(name))))
                .collect(Collectors.toSet());
        existing.setAuthors(authors);
        existing.setCreatedBy(user);

        Book saved = bookRepository.save(existing);
        return AppResponse.success("Book updated successfully", mapToResponse(saved), HttpStatus.CREATED.value());

    }

    @Override
    public AppResponse<Page<BookResponse>> search(String title, String isbn, String publisher, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        Page<Book> results = bookRepository.searchBooks(title, isbn, publisher, from, to, pageable);
        Page<BookResponse> mapped = results.map(this::mapToResponse);
        return AppResponse.success("Search completed", mapped, HttpStatus.CREATED.value());
    }

    @Transactional
    @Override
    public AppResponse<String> uploadCover(Long bookId, MultipartFile file) {
        try {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"));

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Path.of(uploadDir, filename);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            book.setCoverUrl(path.toString());
            bookRepository.save(book);

            return AppResponse.success("Cover uploaded successfully", path.toString(), HttpStatus.CREATED.value());
        } catch (IOException e) {
            return AppResponse.failure("Error uploading file: " + e.getMessage(), HttpStatus.CREATED.value());
        }
    }

    @Override
    public AppResponse<List<Checkout>> listCheckedOutBooks() {
        List<Checkout> checkouts = checkoutRepository.findByReturnDateIsNull();
        return AppResponse.success("Checked out books retrieved", checkouts, HttpStatus.CREATED.value());
    }

    @Override
    public AppResponse<List<Map<String, Object>>> checkedOutInfoForLibrarian() {
        List<Checkout> checkouts = checkoutRepository.findByReturnDateIsNull();

        List<Map<String, Object>> details = checkouts.stream().map(c -> {
            Map<String, Object> map = new HashMap<>();
            map.put("bookTitle", c.getBook().getTitle());
            map.put("reader", c.getReader().getFirstName() + " " + c.getReader().getLastName());
            map.put("checkoutDate", c.getCheckoutDate());
            map.put("dueDate", c.getDueDate());
            long daysOverdue = ChronoUnit.DAYS.between(c.getDueDate(), LocalDateTime.now());
            map.put("daysRemaining", daysOverdue < 0 ? Math.abs(daysOverdue) : 0);
            map.put("daysOverdue", daysOverdue > 0 ? daysOverdue : 0);
            return map;
        }).collect(Collectors.toList());

        return AppResponse.success("Checkout details retrieved", details, HttpStatus.CREATED.value());
    }

    private BookResponse mapToResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .publisher(book.getPublisher())
                .revision(book.getRevision())
                .genre(book.getGenre())
                .description(book.getDescription())
                .publishedDate(book.getPublishedDate())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .coverUrl(book.getCoverUrl())
                .dateAdded(book.getDateAdded())
                .authors(book.getAuthors().stream().map(Author::getName).collect(Collectors.toSet()))
                .build();
    }
}
