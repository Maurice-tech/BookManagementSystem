package com.booksystem.bookManagement.entity;

import com.booksystem.bookManagement.entity.enums.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book_users")
public class BookUsers extends BaseEntity {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    private boolean isVerified = false;
}
