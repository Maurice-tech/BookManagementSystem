package com.booksystem.bookManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "authors")
public class Author extends BaseEntity {

    @Column(nullable = false)
    private String name;

    // helper convenience
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();
}
