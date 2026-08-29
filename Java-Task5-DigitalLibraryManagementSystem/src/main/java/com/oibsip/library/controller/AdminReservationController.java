package com.oibsip.library.controller;

import com.oibsip.library.model.BookReservation;
import com.oibsip.library.model.User;
import com.oibsip.library.service.ReservationService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminReservationController {

    private final ReservationService reservationService;


    public AdminReservationController(
            ReservationService reservationService) {

        this.reservationService = reservationService;
    }


    // =====================================================
    // ADMIN - VIEW ALL RESERVATIONS
    // =====================================================

    @GetMapping("/reservations")
    public String reservations(
            HttpSession session,
            Model model) {

        User admin =
                getLoggedInUser(session);


        // -------------------------------------------------
        // LOGIN CHECK
        // -------------------------------------------------

        if (admin == null) {

            return "redirect:/login";
        }


        // -------------------------------------------------
        // ADMIN CHECK
        // -------------------------------------------------

        if (!"ADMIN".equalsIgnoreCase(
                admin.getRole())) {

            return "redirect:/user/dashboard";
        }


        // -------------------------------------------------
        // GET ALL RESERVATIONS
        // -------------------------------------------------

        List<BookReservation> reservations =
                reservationService
                        .getAllReservations();


        // -------------------------------------------------
        // SEND DATA TO PAGE
        // -------------------------------------------------

        model.addAttribute(
                "admin",
                admin
        );

        model.addAttribute(
                "reservations",
                reservations
        );


        return "admin/reservations";
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