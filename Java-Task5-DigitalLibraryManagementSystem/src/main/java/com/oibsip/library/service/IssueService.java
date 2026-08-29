package com.oibsip.library.service;

import com.oibsip.library.model.Book;
import com.oibsip.library.model.BookReservation;
import com.oibsip.library.model.Issue;
import com.oibsip.library.model.User;
import com.oibsip.library.repository.BookReservationRepository;
import com.oibsip.library.repository.IssueRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final BookService bookService;
    private final NotificationService notificationService;
    private final BookReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final FineService fineService;

    private static final int LOAN_PERIOD_DAYS = 14;

    public IssueService(
            IssueRepository issueRepository,
            BookService bookService,
            NotificationService notificationService,
            BookReservationRepository reservationRepository,
            ReservationService reservationService,
            FineService fineService) {

        this.issueRepository = issueRepository;
        this.bookService = bookService;
        this.notificationService = notificationService;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.fineService = fineService;
    }


    // =========================================================
    // ISSUE / BORROW BOOK
    // =========================================================

    public Issue issueBook(
            User user,
            Book book) {

        // -----------------------------------------------------
        // CHECK BOOK AVAILABILITY
        // -----------------------------------------------------

        if (book.getAvailableQuantity() <= 0) {

            throw new RuntimeException(
                    "Book is currently unavailable."
            );
        }


        // -----------------------------------------------------
        // CHECK WHETHER USER ALREADY HAS THIS BOOK
        // -----------------------------------------------------

        Optional<Issue> existingIssue =
                issueRepository.findByUserAndBookAndStatus(
                        user,
                        book,
                        "ISSUED"
                );

        if (existingIssue.isPresent()) {

            throw new RuntimeException(
                    "You have already issued this book."
            );
        }


        // -----------------------------------------------------
        // DECREASE AVAILABLE QUANTITY
        // -----------------------------------------------------

        bookService.decreaseAvailableQuantity(book);


        // -----------------------------------------------------
        // CREATE ISSUE RECORD
        // -----------------------------------------------------

        Issue issue = new Issue();

        issue.setUser(user);

        issue.setBook(book);

        issue.setIssueDate(
                LocalDate.now()
        );

        issue.setDueDate(
                LocalDate.now()
                        .plusDays(LOAN_PERIOD_DAYS)
        );

        issue.setStatus("ISSUED");


        // -----------------------------------------------------
        // SAVE ISSUE
        // -----------------------------------------------------

        Issue savedIssue =
                issueRepository.save(issue);


        // =====================================================
        // FULFILL USER'S ACTIVE RESERVATION
        // =====================================================

        reservationService.fulfillReservation(
                user,
                book
        );


        return savedIssue;
    }


    // =========================================================
    // CHECK WHETHER USER ALREADY HAS THIS BOOK
    // =========================================================

    public boolean hasActiveIssue(
            User user,
            Book book) {

        return issueRepository
                .findByUserAndBookAndStatus(
                        user,
                        book,
                        "ISSUED"
                )
                .isPresent();
    }


    // =========================================================
    // RETURN BOOK
    // =========================================================

    public Issue returnBook(
            Long issueId) {

        // -----------------------------------------------------
        // FIND ISSUE
        // -----------------------------------------------------

        Issue issue =
                issueRepository.findById(issueId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Issue record not found."
                                )
                        );


        // -----------------------------------------------------
        // CHECK WHETHER ALREADY RETURNED
        // -----------------------------------------------------

        if (!"ISSUED".equalsIgnoreCase(
                issue.getStatus())) {

            throw new RuntimeException(
                    "This book has already been returned."
            );
        }


        // -----------------------------------------------------
        // SAVE BOOK REFERENCE
        // -----------------------------------------------------

        Book returnedBook =
                issue.getBook();


        // -----------------------------------------------------
        // SET RETURN INFORMATION
        // -----------------------------------------------------

        issue.setReturnDate(
                LocalDate.now()
        );

        issue.setStatus(
                "RETURNED"
        );


        // -----------------------------------------------------
        // CALCULATE AND SAVE FINE
        // -----------------------------------------------------

        fineService.calculateAndSaveFine(
                issue
        );


        // -----------------------------------------------------
        // INCREASE AVAILABLE QUANTITY
        // -----------------------------------------------------

        bookService.increaseAvailableQuantity(
                returnedBook
        );


        // -----------------------------------------------------
        // SAVE RETURNED ISSUE
        // -----------------------------------------------------

        Issue savedIssue =
                issueRepository.save(issue);


        // =====================================================
        // FIND ACTIVE RESERVATIONS FOR RETURNED BOOK
        // =====================================================

        List<BookReservation> reservations =
                reservationRepository
                        .findByBookAndStatus(
                                returnedBook,
                                "ACTIVE"
                        );


        // =====================================================
        // SEND NOTIFICATION TO RESERVED USERS
        // =====================================================

        for (BookReservation reservation :
                reservations) {

            User reservedUser =
                    reservation.getUser();

            String message =
                    "The book '"
                            + returnedBook.getTitle()
                            + "' is now available. "
                            + "You can issue it now.";

            notificationService.createNotification(
                    reservedUser,
                    message
            );
        }


        return savedIssue;
    }


    // =========================================================
    // GET ALL ISSUES
    // =========================================================

    public List<Issue> getAllIssues() {

        return issueRepository.findAll();
    }


    // =========================================================
    // GET USER ISSUES
    // =========================================================

    public List<Issue> getUserIssues(
            User user) {

        return issueRepository.findByUser(
                user
        );
    }


    // =========================================================
    // GET ACTIVE ISSUES OF USER
    // =========================================================

    public List<Issue> getActiveIssues(
            User user) {

        return issueRepository.findByUserAndStatus(
                user,
                "ISSUED"
        );
    }


    // =========================================================
    // GET ALL CURRENTLY ISSUED BOOKS
    // =========================================================

    public List<Issue> getIssuedBooks() {

        return issueRepository.findByStatus(
                "ISSUED"
        );
    }


    // =========================================================
    // GET ISSUE BY ID
    // =========================================================

    public Optional<Issue> getIssueById(
            Long id) {

        return issueRepository.findById(id);
    }


    // =========================================================
    // GET OVERDUE ISSUES
    // =========================================================

    public List<Issue> getOverdueIssues() {

        return issueRepository
                .findByDueDateBeforeAndStatus(
                        LocalDate.now(),
                        "ISSUED"
                );
    }
}