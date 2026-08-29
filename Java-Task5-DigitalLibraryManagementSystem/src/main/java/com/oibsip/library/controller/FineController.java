package com.oibsip.library.controller;

import com.oibsip.library.model.Fine;
import com.oibsip.library.model.User;
import com.oibsip.library.service.FineService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class FineController {

    private final FineService fineService;

    public FineController(
            FineService fineService) {

        this.fineService = fineService;
    }


    // =====================================================
    // MY FINES
    // =====================================================

    @GetMapping("/fines")
    public String myFines(
            HttpSession session,
            Model model) {

        User user =
                getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        List<Fine> fines =
                fineService.getUserFines(user);

        model.addAttribute(
                "user",
                user
        );

        model.addAttribute(
                "fines",
                fines
        );

        return "user/fines";
    }


    // =====================================================
    // PAY FINE
    // =====================================================

    @PostMapping("/fines/pay/{id}")
    public String payFine(
            @PathVariable Long id,
            HttpSession session) {

        User user =
                getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        try {

            Fine fine =
                    fineService.getFineById(id)
                            .orElseThrow(
                                    () -> new RuntimeException(
                                            "Fine not found."
                                    )
                            );

            if (!fine.getUser().getId()
                    .equals(user.getId())) {

                throw new RuntimeException(
                        "You cannot pay another user's fine."
                );
            }

            fineService.payFine(id);

        } catch (RuntimeException e) {

            System.out.println(
                    "Pay fine error: "
                            + e.getMessage()
            );
        }

        return "redirect:/user/fines";
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