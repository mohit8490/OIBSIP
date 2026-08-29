package com.oibsip.library.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "queries")
public class Query {

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
    // SUBJECT
    // =====================================================

    @Column(nullable = false)
    private String subject;


    // =====================================================
    // QUERY MESSAGE
    // =====================================================

    @Column(nullable = false, length = 1000)
    private String message;


    // =====================================================
    // ADMIN RESPONSE
    // =====================================================

    @Column(length = 1000)
    private String response;


    // =====================================================
    // STATUS
    // =====================================================

    @Column(nullable = false)
    private String status;


    // =====================================================
    // CREATED AT
    // =====================================================

    @Column(nullable = false)
    private LocalDateTime createdAt;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public Query() {
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
    // GET SUBJECT
    // =====================================================

    public String getSubject() {
        return subject;
    }


    // =====================================================
    // SET SUBJECT
    // =====================================================

    public void setSubject(String subject) {
        this.subject = subject;
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
    // GET RESPONSE
    // =====================================================

    public String getResponse() {
        return response;
    }


    // =====================================================
    // SET RESPONSE
    // =====================================================

    public void setResponse(String response) {
        this.response = response;
    }


    // =====================================================
    // GET STATUS
    // =====================================================

    public String getStatus() {
        return status;
    }


    // =====================================================
    // SET STATUS
    // =====================================================

    public void setStatus(String status) {
        this.status = status;
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