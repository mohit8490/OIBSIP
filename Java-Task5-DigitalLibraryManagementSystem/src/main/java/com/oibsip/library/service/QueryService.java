package com.oibsip.library.service;

import com.oibsip.library.model.Query;
import com.oibsip.library.model.User;
import com.oibsip.library.repository.QueryRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueryService {

    private final QueryRepository queryRepository;
    private final NotificationService notificationService;


    public QueryService(
            QueryRepository queryRepository,
            NotificationService notificationService) {

        this.queryRepository = queryRepository;
        this.notificationService = notificationService;
    }


    // =====================================================
    // CREATE QUERY
    // =====================================================

    public Query createQuery(
            User user,
            String message) {

        // -------------------------------------------------
        // CHECK MESSAGE
        // -------------------------------------------------

        if (message == null ||
                message.trim().isEmpty()) {

            throw new RuntimeException(
                    "Query message cannot be empty."
            );
        }


        // -------------------------------------------------
        // CREATE QUERY
        // -------------------------------------------------

        Query query = new Query();


        // USER

        query.setUser(user);


        // SUBJECT
        // Database requires this field

        query.setSubject(
                "Library Query"
        );


        // MESSAGE

        query.setMessage(
                message.trim()
        );


        // STATUS

        query.setStatus(
                "PENDING"
        );


        // CREATED DATE

        query.setCreatedAt(
                LocalDateTime.now()
        );


        // SAVE

        return queryRepository.save(
                query
        );
    }


    // =====================================================
    // GET USER QUERIES
    // =====================================================

    public List<Query> getUserQueries(
            User user) {

        return queryRepository.findByUser(
                user
        );
    }


    // =====================================================
    // GET ALL QUERIES
    // =====================================================

    public List<Query> getAllQueries() {

        return queryRepository.findAll();
    }


    // =====================================================
    // RESPOND TO QUERY
    // =====================================================

    public Query respondToQuery(
            Long id,
            String response) {

        // -------------------------------------------------
        // CHECK RESPONSE
        // -------------------------------------------------

        if (response == null ||
                response.trim().isEmpty()) {

            throw new RuntimeException(
                    "Response cannot be empty."
            );
        }


        // -------------------------------------------------
        // FIND QUERY
        // -------------------------------------------------

        Query query =
                queryRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Query not found."
                                )
                        );


        // -------------------------------------------------
        // SET RESPONSE
        // -------------------------------------------------

        query.setResponse(
                response.trim()
        );


        // -------------------------------------------------
        // UPDATE STATUS
        // -------------------------------------------------

        query.setStatus(
                "ANSWERED"
        );


        // -------------------------------------------------
        // SAVE QUERY
        // -------------------------------------------------

        Query savedQuery =
                queryRepository.save(
                        query
                );


        // =================================================
        // NOTIFY USER
        // =================================================

        String notificationMessage =
                "Your library query has been answered: "
                        + response.trim();


        notificationService.createNotification(
                query.getUser(),
                notificationMessage
        );


        return savedQuery;
    }
}