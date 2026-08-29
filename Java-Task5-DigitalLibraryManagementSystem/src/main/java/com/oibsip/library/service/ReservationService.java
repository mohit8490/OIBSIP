package com.oibsip.library.service;

import com.oibsip.library.model.Book;
import com.oibsip.library.model.BookReservation;
import com.oibsip.library.model.User;
import com.oibsip.library.repository.BookReservationRepository;
import com.oibsip.library.repository.IssueRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final BookReservationRepository reservationRepository;
    private final IssueRepository issueRepository;

    public ReservationService(
            BookReservationRepository reservationRepository,
            IssueRepository issueRepository) {

        this.reservationRepository = reservationRepository;
        this.issueRepository = issueRepository;
    }


    // =====================================================
    // RESERVE BOOK
    // =====================================================

    public BookReservation reserveBook(
            User user,
            Book book) {

        // -------------------------------------------------
        // CHECK 1:
        // USER ALREADY HAS THIS BOOK
        // -------------------------------------------------

        boolean alreadyIssued =
                issueRepository
                        .findByUserAndBookAndStatus(
                                user,
                                book,
                                "ISSUED"
                        )
                        .isPresent();

        if (alreadyIssued) {

            throw new RuntimeException(
                    "You already have this book issued. "
                            + "You cannot reserve the same book."
            );
        }


        // -------------------------------------------------
        // CHECK 2:
        // BOOK IS AVAILABLE
        // -------------------------------------------------

        if (book.getAvailableQuantity() > 0) {

            throw new RuntimeException(
                    "Book is currently available. "
                            + "You can issue it directly."
            );
        }


        // -------------------------------------------------
        // CHECK 3:
        // USER ALREADY RESERVED THIS BOOK
        // -------------------------------------------------

        boolean alreadyReserved =
                reservationRepository
                        .existsByUserAndBookAndStatus(
                                user,
                                book,
                                "ACTIVE"
                        );

        if (alreadyReserved) {

            throw new RuntimeException(
                    "You have already reserved this book."
            );
        }


        // -------------------------------------------------
        // CREATE NEW RESERVATION
        // -------------------------------------------------

        BookReservation reservation =
                new BookReservation();

        reservation.setUser(user);

        reservation.setBook(book);

        reservation.setReservationDate(
                LocalDate.now()
        );

        reservation.setStatus("ACTIVE");


        return reservationRepository.save(
                reservation
        );
    }


    // =====================================================
    // CHECK ACTIVE RESERVATION
    // =====================================================

    public boolean hasActiveReservation(
            User user,
            Book book) {

        return reservationRepository
                .existsByUserAndBookAndStatus(
                        user,
                        book,
                        "ACTIVE"
                );
    }


    // =====================================================
    // FULFILL RESERVATION
    // =====================================================

    public void fulfillReservation(
            User user,
            Book book) {

        reservationRepository
                .findByUserAndBookAndStatus(
                        user,
                        book,
                        "ACTIVE"
                )
                .ifPresent(reservation -> {

                    reservation.setStatus(
                            "FULFILLED"
                    );

                    reservationRepository.save(
                            reservation
                    );
                });
    }


    // =====================================================
    // GET USER RESERVATIONS
    // =====================================================

    public List<BookReservation> getUserReservations(
            User user) {

        return reservationRepository.findByUser(
                user
        );
    }


    // =====================================================
    // GET ACTIVE RESERVATIONS
    // =====================================================

    public List<BookReservation> getActiveReservations(
            User user) {

        return reservationRepository
                .findByUserAndStatus(
                        user,
                        "ACTIVE"
                );
    }


    // =====================================================
    // GET ALL RESERVATIONS
    // =====================================================

    public List<BookReservation> getAllReservations() {

        return reservationRepository.findAll();
    }


    // =====================================================
    // CANCEL RESERVATION
    // =====================================================

    public void cancelReservation(
            Long id) {

        BookReservation reservation =
                reservationRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Reservation not found."
                                )
                        );

        reservation.setStatus(
                "CANCELLED"
        );

        reservationRepository.save(
                reservation
        );
    }
}