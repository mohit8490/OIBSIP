package com.oibsip.library.controller;

import com.oibsip.library.model.Book;
import com.oibsip.library.model.Issue;
import com.oibsip.library.model.User;
import com.oibsip.library.service.BookService;
import com.oibsip.library.service.IssueService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class IssueController {

    private final IssueService issueService;
    private final BookService bookService;

    public IssueController(
            IssueService issueService,
            BookService bookService) {

        this.issueService = issueService;
        this.bookService = bookService;
    }

    // =====================================================
    // MY BOOKS
    // =====================================================

    @GetMapping("/my-books")
    public String myBooks(
            HttpSession session,
            Model model) {

        User user = getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        List<Issue> issues =
                issueService.getUserIssues(user);

        model.addAttribute(
                "user",
                user
        );

        model.addAttribute(
                "issues",
                issues
        );

        return "user/my-books";
    }


    // =====================================================
    // ISSUE / BORROW BOOK
    // =====================================================

    @PostMapping("/books/issue/{bookId}")
    public String issueBook(
            @PathVariable Long bookId,
            HttpSession session,
            Model model) {

        User user = getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        try {

            // ---------------------------------------------
            // Find Book
            // ---------------------------------------------

            Book book = bookService
                    .getBookById(bookId)
                    .orElseThrow(
                            () -> new RuntimeException(
                                    "Book not found with ID: "
                                            + bookId
                            )
                    );


            // ---------------------------------------------
            // Issue Book
            // ---------------------------------------------

            issueService.issueBook(
                    user,
                    book
            );


            // ---------------------------------------------
            // Success
            // ---------------------------------------------

            return "redirect:/user/my-books";


        } catch (Exception e) {

            // ---------------------------------------------
            // PRINT ACTUAL ERROR
            // ---------------------------------------------

            System.out.println();
            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "       ISSUE BOOK ERROR"
            );

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "User: "
                            + user.getUsername()
            );

            System.out.println(
                    "Book ID: "
                            + bookId
            );

            System.out.println(
                    "Error: "
                            + e.getMessage()
            );

            e.printStackTrace();

            System.out.println(
                    "=========================================="
            );

            System.out.println();


            // ---------------------------------------------
            // Send Error To Page
            // ---------------------------------------------

            model.addAttribute(
                    "error",
                    "Unable to issue book: "
                            + e.getMessage()
            );


            // ---------------------------------------------
            // Reload Books
            // ---------------------------------------------

            model.addAttribute(
                    "books",
                    bookService.getAllBooks()
            );


            // ---------------------------------------------
            // Keep Logged-In User
            // ---------------------------------------------

            model.addAttribute(
                    "user",
                    user
            );


            // ---------------------------------------------
            // Return To Book Catalogue
            // ---------------------------------------------

            return "user/books";
        }
    }


    // =====================================================
    // RETURN BOOK
    // =====================================================

    @PostMapping("/books/return/{issueId}")
    public String returnBook(
            @PathVariable Long issueId,
            HttpSession session) {

        User user = getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        try {

            issueService.returnBook(
                    issueId
            );

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "       RETURN BOOK ERROR"
            );

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "Issue ID: "
                            + issueId
            );

            System.out.println(
                    "Error: "
                            + e.getMessage()
            );

            e.printStackTrace();

            System.out.println(
                    "=========================================="
            );

            System.out.println();

        }

        return "redirect:/user/my-books";
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