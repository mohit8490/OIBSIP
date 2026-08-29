package com.oibsip.library.service;

import com.oibsip.library.model.Notification;
import com.oibsip.library.model.User;
import com.oibsip.library.repository.NotificationRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(
            NotificationRepository notificationRepository) {

        this.notificationRepository =
                notificationRepository;
    }


    // =====================================================
    // CREATE NOTIFICATION
    // =====================================================

    public Notification createNotification(
            User user,
            String message) {

        Notification notification =
                new Notification();

        notification.setUser(user);

        notification.setMessage(message);

        notification.setRead(false);

        notification.setCreatedAt(
                LocalDateTime.now()
        );

        return notificationRepository.save(
                notification
        );
    }


    // =====================================================
    // GET USER NOTIFICATIONS
    // =====================================================

    public List<Notification> getUserNotifications(
            User user) {

        return notificationRepository
                .findByUserOrderByCreatedAtDesc(
                        user
                );
    }


    // =====================================================
    // GET UNREAD COUNT
    // =====================================================

    public long getUnreadCount(
            User user) {

        return notificationRepository
                .countByUserAndReadFalse(
                        user
                );
    }


    // =====================================================
    // MARK SINGLE NOTIFICATION AS READ
    // =====================================================

    public void markAsRead(
            Long id) {

        Notification notification =
                notificationRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Notification not found."
                                )
                        );

        notification.setRead(true);

        notificationRepository.save(
                notification
        );
    }


    // =====================================================
    // MARK ALL NOTIFICATIONS AS READ
    // =====================================================

    public void markAllAsRead(
            User user) {

        List<Notification> notifications =
                notificationRepository
                        .findByUserOrderByCreatedAtDesc(
                                user
                        );

        for (Notification notification :
                notifications) {

            notification.setRead(true);
        }

        notificationRepository.saveAll(
                notifications
        );
    }


    // =====================================================
    // DELETE NOTIFICATION
    // =====================================================

    public void deleteNotification(
            Long id) {

        Notification notification =
                notificationRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Notification not found."
                                )
                        );

        notificationRepository.delete(
                notification
        );
    }
}