package com.oibsip.library.controller;

import com.oibsip.library.model.Fine;
import com.oibsip.library.model.Issue;
import com.oibsip.library.model.User;

import com.oibsip.library.service.BookService;
import com.oibsip.library.service.FineService;
import com.oibsip.library.service.IssueService;

import com.oibsip.library.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;
    private final IssueService issueService;
    private final UserRepository userRepository;
    private final FineService fineService;


    public AdminController(
            BookService bookService,
            IssueService issueService,
            UserRepository userRepository,
            FineService fineService) {

        this.bookService = bookService;
        this.issueService = issueService;
        this.userRepository = userRepository;
        this.fineService = fineService;
    }


    // =====================================================
    // ADMIN DASHBOARD
    // =====================================================

    @GetMapping("/dashboard")
    public String dashboard(
            HttpSession session,
            Model model) {

        User admin = getLoggedInUser(session);

        if (admin == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(admin.getRole())) {
            return "redirect:/user/dashboard";
        }

        long totalBooks =
                bookService.getAllBooks().size();

        long totalUsers =
                userRepository.count();

        long totalIssuedBooks =
                issueService.getAllIssues()
                        .stream()
                        .filter(issue ->
                                "ISSUED".equalsIgnoreCase(
                                        issue.getStatus()
                                )
                        )
                        .count();


        // =================================================
        // FINE SUMMARY
        // =================================================

        List<Fine> fines =
                fineService.getAllFines();


        long totalFines =
                fines.size();


        long unpaidFines =
                fines.stream()
                        .filter(fine -> !fine.isPaid())
                        .count();


        double unpaidFineAmount =
                fines.stream()
                        .filter(fine -> !fine.isPaid())
                        .mapToDouble(Fine::getAmount)
                        .sum();


        model.addAttribute(
                "admin",
                admin
        );

        model.addAttribute(
                "totalBooks",
                totalBooks
        );

        model.addAttribute(
                "totalUsers",
                totalUsers
        );

        model.addAttribute(
                "totalIssuedBooks",
                totalIssuedBooks
        );

        model.addAttribute(
                "totalFines",
                totalFines
        );

        model.addAttribute(
                "unpaidFines",
                unpaidFines
        );

        model.addAttribute(
                "unpaidFineAmount",
                unpaidFineAmount
        );


        return "admin/dashboard";
    }


    // =====================================================
    // ADMIN - ISSUED BOOKS
    // =====================================================

    @GetMapping("/issued-books")
    public String issuedBooks(
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

        List<Issue> issues =
                issueService.getAllIssues();

        model.addAttribute(
                "admin",
                admin
        );

        model.addAttribute(
                "issues",
                issues
        );

        return "admin/issued-books";
    }


    // =====================================================
    // ADMIN - FINES
    // =====================================================

    @GetMapping("/fines")
    public String fines(
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


        List<Fine> fines =
                fineService.getAllFines();


        double totalUnpaid =
                fines.stream()
                        .filter(fine -> !fine.isPaid())
                        .mapToDouble(Fine::getAmount)
                        .sum();


        double totalPaid =
                fines.stream()
                        .filter(Fine::isPaid)
                        .mapToDouble(Fine::getAmount)
                        .sum();


        model.addAttribute(
                "admin",
                admin
        );

        model.addAttribute(
                "fines",
                fines
        );

        model.addAttribute(
                "totalUnpaid",
                totalUnpaid
        );

        model.addAttribute(
                "totalPaid",
                totalPaid
        );


        return "admin/fines";
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