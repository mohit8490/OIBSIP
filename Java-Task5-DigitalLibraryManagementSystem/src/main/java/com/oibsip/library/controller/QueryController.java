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
@RequestMapping("/user")
public class QueryController {

    private final QueryService queryService;

    public QueryController(
            QueryService queryService) {

        this.queryService = queryService;
    }

    // =====================================================
    // USER QUERY PAGE
    // =====================================================

    @GetMapping("/query")
    public String queryPage(
            HttpSession session,
            Model model) {

        User user = getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        List<Query> queries = queryService.getUserQueries(user);

        model.addAttribute(
                "user",
                user);

        model.addAttribute(
                "queries",
                queries);

        return "user/query";
    }

    // =====================================================
    // SUBMIT QUERY
    // =====================================================

    @PostMapping("/query")
    public String submitQuery(
            @RequestParam String message,
            HttpSession session,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        User user = getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        try {

            queryService.createQuery(
                    user,
                    message);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Query submitted successfully.");

        } catch (RuntimeException e) {

            System.out.println(
                    "==========================================");

            System.out.println(
                    "QUERY SUBMIT ERROR: "
                            + e.getMessage());

            e.printStackTrace();

            System.out.println(
                    "==========================================");

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage());
        }

        return "redirect:/user/query";
    }

    // =====================================================
    // SESSION CHECK
    // =====================================================

    private User getLoggedInUser(
            HttpSession session) {

        Object userObject = session.getAttribute(
                "loggedInUser");

        if (userObject instanceof User) {

            return (User) userObject;
        }

        return null;
    }
}