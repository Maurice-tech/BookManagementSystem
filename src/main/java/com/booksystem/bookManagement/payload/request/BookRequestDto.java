package com.booksystem.bookManagement.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class BookRequestDto {
    private BookRequest book;
    private List<String> authors;
}
