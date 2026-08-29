package com.oibsip.library.controller;

import com.oibsip.library.model.User;
import com.oibsip.library.service.FineService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminFineController {

    private final FineService fineService;


    public AdminFineController(
            FineService fineService) {

        this.fineService = fineService;
    }


    // =====================================================
    // ADMIN - GENERATE OVERDUE FINES
    // =====================================================

    @PostMapping("/fines/generate")
    public String generateFines(
            HttpSession session) {

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
        // GENERATE FINES
        // -------------------------------------------------

        fineService.generateOverdueFines();


        return "redirect:/admin/fines";
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