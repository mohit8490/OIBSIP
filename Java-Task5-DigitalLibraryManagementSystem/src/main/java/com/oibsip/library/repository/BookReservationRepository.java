package com.oibsip.library.repository;

import com.oibsip.library.model.Book;
import com.oibsip.library.model.BookReservation;
import com.oibsip.library.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookReservationRepository
        extends JpaRepository<BookReservation, Long> {

    boolean existsByUserAndBookAndStatus(
            User user,
            Book book,
            String status
    );

    Optional<BookReservation> findByUserAndBookAndStatus(
            User user,
            Book book,
            String status
    );

    List<BookReservation> findByUser(
            User user
    );

    List<BookReservation> findByUserAndStatus(
            User user,
            String status
    );

    List<BookReservation> findByBookAndStatus(
            Book book,
            String status
    );
}