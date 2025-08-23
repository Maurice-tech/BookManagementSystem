package com.booksystem.bookManagement.payload.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String isbn;
    private String revision;
    private LocalDate publishedDate;
    private String publisher;
    private String genre;
    private String description;
    private String coverUrl;
    private Integer totalCopies;
    private Integer availableCopies;
    private LocalDateTime dateAdded;
    private Set<String> authors;
}

