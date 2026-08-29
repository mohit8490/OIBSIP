package com.oibsip.library.controller;

import com.oibsip.library.model.User;
import com.oibsip.library.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminMemberController {

    private final UserService userService;

    public AdminMemberController(
            UserService userService) {

        this.userService = userService;
    }


    // =====================================================
    // ADMIN - VIEW MEMBERS
    // =====================================================

    @GetMapping("/members")
    public String members(
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

        List<User> members =
                userService.getMembers();

        model.addAttribute(
                "admin",
                admin
        );

        model.addAttribute(
                "members",
                members
        );

        return "admin/members";
    }


    // =====================================================
    // ADMIN - DELETE MEMBER
    // =====================================================

    @PostMapping("/members/delete/{id}")
    public String deleteMember(
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

            User member =
                    userService.getUserById(id)
                            .orElseThrow(
                                    () -> new RuntimeException(
                                            "User not found."
                                    )
                            );

            if ("ADMIN".equalsIgnoreCase(
                    member.getRole())) {

                throw new RuntimeException(
                        "Admin account cannot be deleted."
                );
            }

            userService.deleteUser(id);

        } catch (RuntimeException e) {

            System.out.println(
                    "Delete member error: "
                            + e.getMessage()
            );
        }

        return "redirect:/admin/members";
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