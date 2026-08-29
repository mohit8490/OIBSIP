package com.oibsip.library.controller;

import com.oibsip.library.model.Notification;
import com.oibsip.library.model.User;
import com.oibsip.library.service.NotificationService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService =
                notificationService;
    }


    // =====================================================
    // VIEW NOTIFICATIONS
    // =====================================================

    @GetMapping("/notifications")
    public String notifications(
            HttpSession session,
            Model model) {

        User user =
                getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }


        // Get all notifications
        List<Notification> notifications =
                notificationService
                        .getUserNotifications(user);


        // Get unread count
        long unreadCount =
                notificationService
                        .getUnreadCount(user);


        model.addAttribute(
                "user",
                user
        );

        model.addAttribute(
                "notifications",
                notifications
        );

        model.addAttribute(
                "unreadCount",
                unreadCount
        );


        return "user/notifications";
    }


    // =====================================================
    // MARK SINGLE NOTIFICATION AS READ
    // =====================================================

    @PostMapping("/notifications/read/{id}")
    public String markAsRead(
            @PathVariable Long id,
            HttpSession session) {

        User user =
                getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }


        try {

            notificationService.markAsRead(
                    id
            );

        } catch (RuntimeException e) {

            System.out.println(
                    "Notification read error: "
                            + e.getMessage()
            );
        }


        return "redirect:/user/notifications";
    }


    // =====================================================
    // MARK ALL AS READ
    // =====================================================

    @PostMapping("/notifications/read-all")
    public String markAllAsRead(
            HttpSession session) {

        User user =
                getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }


        notificationService.markAllAsRead(
                user
        );


        return "redirect:/user/notifications";
    }


    // =====================================================
    // DELETE NOTIFICATION
    // =====================================================

    @PostMapping("/notifications/delete/{id}")
    public String deleteNotification(
            @PathVariable Long id,
            HttpSession session) {

        User user =
                getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }


        try {

            notificationService.deleteNotification(
                    id
            );

        } catch (RuntimeException e) {

            System.out.println(
                    "Notification delete error: "
                            + e.getMessage()
            );
        }


        return "redirect:/user/notifications";
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