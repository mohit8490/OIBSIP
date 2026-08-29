package com.oibsip.library.controller;

import com.oibsip.library.model.Book;
import com.oibsip.library.model.BookReservation;
import com.oibsip.library.model.User;
import com.oibsip.library.service.BookService;
import com.oibsip.library.service.ReservationService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class ReservationController {

    private final ReservationService reservationService;
    private final BookService bookService;


    public ReservationController(
            ReservationService reservationService,
            BookService bookService) {

        this.reservationService = reservationService;
        this.bookService = bookService;
    }


    // =====================================================
    // MY RESERVATIONS
    // =====================================================

    @GetMapping("/reservations")
    public String myReservations(
            HttpSession session,
            Model model) {

        User user = getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        List<BookReservation> reservations =
                reservationService
                        .getUserReservations(user);

        model.addAttribute(
                "user",
                user
        );

        model.addAttribute(
                "reservations",
                reservations
        );

        return "user/reservations";
    }


    // =====================================================
    // RESERVE BOOK
    // =====================================================

    @PostMapping("/books/reserve/{bookId}")
    public String reserveBook(
            @PathVariable Long bookId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        try {

            Book book =
                    bookService
                            .getBookById(bookId)
                            .orElseThrow(
                                    () -> new RuntimeException(
                                            "Book not found."
                                    )
                            );


            reservationService.reserveBook(
                    user,
                    book
            );


            // ---------------------------------------------
            // SUCCESS
            // ---------------------------------------------

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Book reserved successfully."
            );

            return "redirect:/user/reservations";


        } catch (RuntimeException e) {

            // ---------------------------------------------
            // ERROR
            // ---------------------------------------------

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );

            return "redirect:/user/books";
        }
    }


    // =====================================================
    // CANCEL RESERVATION
    // =====================================================

    @PostMapping("/reservations/cancel/{id}")
    public String cancelReservation(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        try {

            reservationService.cancelReservation(id);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Reservation cancelled successfully."
            );

        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/user/reservations";
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