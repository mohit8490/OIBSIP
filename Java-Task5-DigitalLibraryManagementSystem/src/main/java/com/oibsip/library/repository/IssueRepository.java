package com.oibsip.library.repository;

import com.oibsip.library.model.Book;
import com.oibsip.library.model.Issue;
import com.oibsip.library.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    // =====================================================
    // GET ALL ISSUES OF A USER
    // =====================================================

    List<Issue> findByUser(User user);


    // =====================================================
    // GET ISSUES BY STATUS
    // =====================================================

    List<Issue> findByStatus(String status);


    // =====================================================
    // GET ISSUES OF USER BY STATUS
    // =====================================================

    List<Issue> findByUserAndStatus(
            User user,
            String status
    );


    // =====================================================
    // CHECK WHETHER USER HAS A PARTICULAR BOOK ISSUED
    // =====================================================

    Optional<Issue> findByUserAndBookAndStatus(
            User user,
            Book book,
            String status
    );


    // =====================================================
    // GET OVERDUE ISSUES
    // =====================================================

    List<Issue> findByDueDateBeforeAndStatus(
            LocalDate date,
            String status
    );
}