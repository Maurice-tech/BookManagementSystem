package com.booksystem.bookManagement.repository;

import com.booksystem.bookManagement.entity.BookUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookUsersRepository extends JpaRepository<BookUsers, Long> {

    Optional<BookUsers> findByEmail(String email);
    Boolean existsByEmail(String email);
}
