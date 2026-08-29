package com.oibsip.library.controller;

import com.oibsip.library.model.Query;
import com.oibsip.library.model.User;
import com.oibsip.library.service.QueryService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminQueryController {

    private final QueryService queryService;

    public AdminQueryController(
            QueryService queryService) {

        this.queryService = queryService;
    }


    // =====================================================
    // ADMIN - VIEW ALL QUERIES
    // =====================================================

    @GetMapping("/queries")
    public String queries(
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

        List<Query> queries =
                queryService.getAllQueries();

        model.addAttribute(
                "admin",
                admin
        );

        model.addAttribute(
                "queries",
                queries
        );

        return "admin/queries";
    }


    // =====================================================
    // ADMIN - RESPOND TO QUERY
    // =====================================================

    @PostMapping("/queries/respond/{id}")
    public String respondToQuery(
            @PathVariable Long id,
            @RequestParam String response,
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

            queryService.respondToQuery(
                    id,
                    response
            );

        } catch (RuntimeException e) {

            System.out.println(
                    "Query response error: "
                            + e.getMessage()
            );
        }

        return "redirect:/admin/queries";
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