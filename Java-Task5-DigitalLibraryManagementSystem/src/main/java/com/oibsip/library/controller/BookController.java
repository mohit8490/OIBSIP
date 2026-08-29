package com.oibsip.library.controller;

import com.oibsip.library.model.Book;
import com.oibsip.library.model.User;

import com.oibsip.library.service.BookService;
import com.oibsip.library.service.IssueService;
import com.oibsip.library.service.ReservationService;
import com.oibsip.library.service.NotificationService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;
    private final IssueService issueService;
    private final ReservationService reservationService;
    private final NotificationService notificationService;


    public BookController(
            BookService bookService,
            IssueService issueService,
            ReservationService reservationService,
            NotificationService notificationService) {

        this.bookService = bookService;
        this.issueService = issueService;
        this.reservationService = reservationService;
        this.notificationService = notificationService;
    }


    // =====================================================
    // USER DASHBOARD
    // =====================================================

    @GetMapping("/user/dashboard")
    public String dashboard(
            HttpSession session,
            Model model) {

        User user =
                getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        long unreadCount =
                notificationService.getUnreadCount(user);

        model.addAttribute(
                "user",
                user
        );

        model.addAttribute(
                "unreadCount",
                unreadCount
        );

        return "user/dashboard";
    }


    // =====================================================
    // USER - VIEW BOOKS
    // =====================================================

    @GetMapping("/user/books")
    public String books(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String error,
            HttpSession session,
            Model model) {

        User user =
                getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }


        List<Book> books;


        // =================================================
        // SEARCH
        // =================================================

        if (keyword != null && !keyword.isBlank()) {

            books =
                    bookService.searchBooks(keyword);

        }


        // =================================================
        // CATEGORY
        // =================================================

        else if (category != null && !category.isBlank()) {

            books =
                    bookService.getBooksByCategory(category);

        }


        // =================================================
        // ALL BOOKS
        // =================================================

        else {

            books =
                    bookService.getAllBooks();
        }


        // =================================================
        // FIND BOOKS ALREADY ISSUED BY CURRENT USER
        // =================================================

        List<Long> issuedBookIds =
                books.stream()
                        .filter(book ->
                                issueService.hasActiveIssue(
                                        user,
                                        book
                                )
                        )
                        .map(Book::getId)
                        .toList();


        // =================================================
        // FIND BOOKS ALREADY RESERVED BY CURRENT USER
        // =================================================

        List<Long> reservedBookIds =
                books.stream()
                        .filter(book ->
                                reservationService.hasActiveReservation(
                                        user,
                                        book
                                )
                        )
                        .map(Book::getId)
                        .toList();


        // =================================================
        // GET UNREAD NOTIFICATION COUNT
        // =================================================

        long unreadCount =
                notificationService.getUnreadCount(user);


        // =================================================
        // SEND DATA TO THYMELEAF
        // =================================================

        model.addAttribute(
                "user",
                user
        );

        model.addAttribute(
                "books",
                books
        );

        model.addAttribute(
                "keyword",
                keyword
        );

        model.addAttribute(
                "category",
                category
        );

        model.addAttribute(
                "issuedBookIds",
                issuedBookIds
        );

        model.addAttribute(
                "reservedBookIds",
                reservedBookIds
        );

        model.addAttribute(
                "unreadCount",
                unreadCount
        );

        model.addAttribute(
                "error",
                error
        );


        return "user/books";
    }


    // =====================================================
    // ADMIN - MANAGE BOOKS
    // =====================================================

    @GetMapping("/admin/books")
    public String adminBooks(
            HttpSession session,
            Model model) {

        User admin =
                getLoggedInUser(session);

        if (admin == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(
                admin.getRole())) {

            return "redirect:/user/dashboard";
        }


        List<Book> books =
                bookService.getAllBooks();


        model.addAttribute(
                "admin",
                admin
        );

        model.addAttribute(
                "books",
                books
        );


        return "admin/books";
    }


    // =====================================================
    // ADMIN - ADD BOOK PAGE
    // =====================================================

    @GetMapping("/admin/books/add")
    public String addBookPage(
            HttpSession session,
            Model model) {

        User admin =
                getLoggedInUser(session);

        if (admin == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(
                admin.getRole())) {

            return "redirect:/user/dashboard";
        }


        model.addAttribute(
                "admin",
                admin
        );

        model.addAttribute(
                "book",
                new Book()
        );


        return "admin/add-book";
    }


    // =====================================================
    // ADMIN - ADD BOOK PROCESS
    // =====================================================

    @PostMapping("/admin/books/add")
    public String addBook(
            @ModelAttribute Book book,
            HttpSession session,
            Model model) {

        User admin =
                getLoggedInUser(session);

        if (admin == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(
                admin.getRole())) {

            return "redirect:/user/dashboard";
        }


        try {

            bookService.addBook(book);

            return "redirect:/admin/books";


        } catch (RuntimeException e) {

            model.addAttribute(
                    "admin",
                    admin
            );

            model.addAttribute(
                    "book",
                    book
            );

            model.addAttribute(
                    "error",
                    e.getMessage()
            );


            return "admin/add-book";
        }
    }


    // =====================================================
    // ADMIN - EDIT BOOK PAGE
    // =====================================================

    @GetMapping("/admin/books/edit/{id}")
    public String editBookPage(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        User admin =
                getLoggedInUser(session);

        if (admin == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(
                admin.getRole())) {

            return "redirect:/user/dashboard";
        }


        Book book =
                bookService
                        .getBookById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Book not found."
                                )
                        );


        model.addAttribute(
                "admin",
                admin
        );

        model.addAttribute(
                "book",
                book
        );


        return "admin/edit-book";
    }


    // =====================================================
    // ADMIN - UPDATE BOOK
    // =====================================================

    @PostMapping("/admin/books/edit/{id}")
    public String updateBook(
            @PathVariable Long id,
            @ModelAttribute Book book,
            HttpSession session,
            Model model) {

        User admin =
                getLoggedInUser(session);

        if (admin == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(
                admin.getRole())) {

            return "redirect:/user/dashboard";
        }


        try {

            bookService.updateBook(
                    id,
                    book
            );

            return "redirect:/admin/books";


        } catch (RuntimeException e) {

            model.addAttribute(
                    "admin",
                    admin
            );

            model.addAttribute(
                    "book",
                    book
            );

            model.addAttribute(
                    "error",
                    e.getMessage()
            );


            return "admin/edit-book";
        }
    }


    // =====================================================
    // ADMIN - DELETE BOOK
    // =====================================================

    @PostMapping("/admin/books/delete/{id}")
    public String deleteBook(
            @PathVariable Long id,
            HttpSession session) {

        User admin =
                getLoggedInUser(session);

        if (admin == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(
                admin.getRole())) {

            return "redirect:/user/dashboard";
        }


        try {

            bookService.deleteBook(id);


        } catch (RuntimeException e) {

            System.out.println(
                    "Unable to delete book: "
                            + e.getMessage()
            );
        }


        return "redirect:/admin/books";
    }


    // =====================================================
    // LOGOUT
    // =====================================================

    @GetMapping("/user/logout")
    public String logout(
            HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }


    // =====================================================
    // SESSION CHECK
    // =====================================================

    private User getLoggedInUser(
            HttpSession session) {

        Object userObject =
                session.getAttribute(
                        "loggedInUser"
                );


        if (userObject instanceof User) {

            return (User) userObject;
        }


        return null;
    }
}