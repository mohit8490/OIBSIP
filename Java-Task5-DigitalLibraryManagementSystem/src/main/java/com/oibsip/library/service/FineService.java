package com.oibsip.library.service;

import com.oibsip.library.model.Fine;
import com.oibsip.library.model.Issue;
import com.oibsip.library.model.User;
import com.oibsip.library.repository.FineRepository;
import com.oibsip.library.repository.IssueRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class FineService {

    private final FineRepository fineRepository;
    private final IssueRepository issueRepository;

    private static final double FINE_PER_DAY = 5.0;


    public FineService(
            FineRepository fineRepository,
            IssueRepository issueRepository) {

        this.fineRepository = fineRepository;
        this.issueRepository = issueRepository;
    }


    // =====================================================
    // CALCULATE FINE
    // =====================================================

    public double calculateFine(
            Issue issue) {

        if (issue == null) {
            return 0;
        }

        if (issue.getDueDate() == null) {
            return 0;
        }

        LocalDate endDate;

        if (issue.getReturnDate() != null) {

            endDate =
                    issue.getReturnDate();

        } else {

            endDate =
                    LocalDate.now();
        }


        if (!endDate.isAfter(
                issue.getDueDate())) {

            return 0;
        }


        long overdueDays =
                ChronoUnit.DAYS.between(
                        issue.getDueDate(),
                        endDate
                );


        return overdueDays *
                FINE_PER_DAY;
    }


    // =====================================================
    // CREATE FINE
    // =====================================================

    public Fine createFine(
            Issue issue) {

        if (issue == null) {
            return null;
        }


        if (fineRepository
                .findByIssueId(issue.getId())
                .isPresent()) {

            return fineRepository
                    .findByIssueId(
                            issue.getId()
                    )
                    .get();
        }


        double amount =
                calculateFine(issue);


        if (amount <= 0) {
            return null;
        }


        Fine fine =
                new Fine();


        fine.setUser(
                issue.getUser()
        );


        fine.setIssue(
                issue
        );


        fine.setAmount(
                amount
        );


        fine.setPaid(
                false
        );


        fine.setCreatedAt(
                LocalDateTime.now()
        );


        return fineRepository.save(
                fine
        );
    }


    // =====================================================
    // CALCULATE AND SAVE FINE
    // =====================================================

    public Fine calculateAndSaveFine(
            Issue issue) {

        if (issue == null) {
            return null;
        }


        double amount =
                calculateFine(issue);


        if (amount <= 0) {
            return null;
        }


        Fine fine =
                fineRepository
                        .findByIssueId(
                                issue.getId()
                        )
                        .orElse(null);


        if (fine == null) {

            fine =
                    new Fine();


            fine.setIssue(
                    issue
            );


            fine.setUser(
                    issue.getUser()
            );


            fine.setCreatedAt(
                    LocalDateTime.now()
            );


            fine.setPaid(
                    false
            );
        }


        fine.setAmount(
                amount
        );


        return fineRepository.save(
                fine
        );
    }


    // =====================================================
    // GENERATE FINES FOR CURRENT OVERDUE BOOKS
    // =====================================================

    public void generateOverdueFines() {

        List<Issue> overdueIssues =
                issueRepository
                        .findByDueDateBeforeAndStatus(
                                LocalDate.now(),
                                "ISSUED"
                        );


        for (Issue issue :
                overdueIssues) {

            calculateAndSaveFine(
                    issue
            );
        }
    }


    // =====================================================
    // GET USER FINES
    // =====================================================

    public List<Fine> getUserFines(
            User user) {

        return fineRepository
                .findByUser(user);
    }


    // =====================================================
    // GET ALL FINES
    // =====================================================

    public List<Fine> getAllFines() {

        return fineRepository.findAll();
    }


    // =====================================================
    // GET UNPAID FINES
    // =====================================================

    public List<Fine> getUnpaidFines(
            User user) {

        return fineRepository
                .findByUserAndPaid(
                        user,
                        false
                );
    }


    // =====================================================
    // GET PAID FINES
    // =====================================================

    public List<Fine> getPaidFines(
            User user) {

        return fineRepository
                .findByUserAndPaid(
                        user,
                        true
                );
    }


    // =====================================================
    // GET FINE BY ID
    // =====================================================

    public Optional<Fine> getFineById(
            Long id) {

        return fineRepository.findById(id);
    }


    // =====================================================
    // PAY FINE
    // =====================================================

    public Fine payFine(
            Long id) {

        Fine fine =
                fineRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Fine not found."
                                )
                        );


        if (fine.isPaid()) {

            throw new RuntimeException(
                    "Fine is already paid."
            );
        }


        fine.setPaid(
                true
        );


        return fineRepository.save(
                fine
        );
    }


    // =====================================================
    // GET FINE BY ISSUE
    // =====================================================

    public Fine getFineByIssueId(
            Long issueId) {

        return fineRepository
                .findByIssueId(issueId)
                .orElse(null);
    }
}