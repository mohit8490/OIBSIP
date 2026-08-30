package com.oibsip.library.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // =====================================================
    // USER
    // =====================================================

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    // =====================================================
    // MESSAGE
    // =====================================================

    @Column(nullable = false, length = 500)
    private String message;


    // =====================================================
    // READ STATUS
    // =====================================================

    @Column(name = "is_read", nullable = false)
    private boolean read = false;


    // =====================================================
    // CREATED AT
    // =====================================================

    @Column(nullable = false)
    private LocalDateTime createdAt;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public Notification() {
    }


    // =====================================================
    // GET ID
    // =====================================================

    public Long getId() {
        return id;
    }


    // =====================================================
    // SET ID
    // =====================================================

    public void setId(Long id) {
        this.id = id;
    }


    // =====================================================
    // GET USER
    // =====================================================

    public User getUser() {
        return user;
    }


    // =====================================================
    // SET USER
    // =====================================================

    public void setUser(User user) {
        this.user = user;
    }


    // =====================================================
    // GET MESSAGE
    // =====================================================

    public String getMessage() {
        return message;
    }


    // =====================================================
    // SET MESSAGE
    // =====================================================

    public void setMessage(String message) {
        this.message = message;
    }


    // =====================================================
    // GET READ
    // =====================================================

    public boolean isRead() {
        return read;
    }


    // =====================================================
    // SET READ
    // =====================================================

    public void setRead(boolean read) {
        this.read = read;
    }


    // =====================================================
    // GET CREATED AT
    // =====================================================

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    // =====================================================
    // SET CREATED AT
    // =====================================================

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }
}