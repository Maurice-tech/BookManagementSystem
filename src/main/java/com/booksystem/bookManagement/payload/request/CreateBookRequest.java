package com.booksystem.bookManagement.payload.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateBookRequest {
    private String title;
    private String isbn;
    private String revision;
    private LocalDate publishedDate;
    private String publisher;
    private String genre;
    private String description;
    private Integer totalCopies = 1;
    private List<String> authors;
}

