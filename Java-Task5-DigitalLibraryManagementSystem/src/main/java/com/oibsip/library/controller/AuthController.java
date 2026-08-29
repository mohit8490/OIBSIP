package com.oibsip.library.controller;

import com.oibsip.library.model.User;
import com.oibsip.library.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // =========================
    // HOME PAGE
    // =========================

    @GetMapping("/")
    public String home() {
        return "index";
    }

    // =========================
    // LOGIN PAGE
    // =========================

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // =========================
    // LOGIN PROCESS
    // =========================

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        Optional<User> user =
                userService.login(username, password);

        if (user.isPresent()) {

            session.setAttribute("loggedInUser", user.get());

            if ("ADMIN".equalsIgnoreCase(user.get().getRole())) {
                return "redirect:/admin/dashboard";
            }

            return "redirect:/user/dashboard";
        }

        model.addAttribute(
                "error",
                "Invalid username or password."
        );

        return "login";
    }

    // =========================
    // REGISTER PAGE
    // =========================

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }

    // =========================
    // REGISTER PROCESS
    // =========================

    @PostMapping("/register")
    public String register(
            @ModelAttribute User user,
            Model model) {

        try {

            userService.registerUser(user);

            model.addAttribute(
                    "success",
                    "Registration successful! Please login."
            );

            return "login";

        } catch (RuntimeException e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            return "register";
        }
    }

    // =========================
    // LOGOUT
    // =========================

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}