package com.booksystem.bookManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "books")
public class Book extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(unique = true)
    private String isbn;
    private String revision;
    private LocalDate publishedDate;
    private String publisher;
    private String genre;
    private String description;

    // cover url/file path
    private String coverUrl;

    private LocalDateTime dateAdded;

    // copies handling
    private Integer totalCopies = 1;
    private Integer availableCopies = 1;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();

    public void setCreatedBy(BookUsers user) {
    }
}
