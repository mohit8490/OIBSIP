package com.oibsip.reservation.ui;

import com.oibsip.reservation.model.User;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private User loggedInUser;

    public DashboardFrame(User user) {

        this.loggedInUser = user;

        setTitle("Online Reservation System - Dashboard");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();

        setVisible(true);
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 40, 30, 40
                )
        );

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(
                new GridLayout(2, 1)
        );

        JLabel titleLabel = new JLabel(
                "ONLINE RESERVATION SYSTEM",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + loggedInUser.getUsername(),
                SwingConstants.CENTER
        );

        welcomeLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        headerPanel.add(titleLabel);
        headerPanel.add(welcomeLabel);

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =========================
        // BUTTONS
        // =========================

        JPanel buttonPanel = new JPanel(
                new GridLayout(2, 2, 20, 20)
        );

        JButton bookButton =
                new JButton("BOOK RESERVATION");

        JButton searchButton =
                new JButton("SEARCH RESERVATION");

        JButton cancelButton =
                new JButton("CANCEL RESERVATION");

        JButton logoutButton =
                new JButton("LOGOUT");

        Font buttonFont =
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                );

        bookButton.setFont(buttonFont);
        searchButton.setFont(buttonFont);
        cancelButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);

        buttonPanel.add(bookButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        bookButton.addActionListener(
                e -> openReservation()
        );

        searchButton.addActionListener(
                e -> openCancellation()
        );

        cancelButton.addActionListener(
                e -> openCancellation()
        );

        logoutButton.addActionListener(
                e -> logout()
        );

        add(mainPanel);
    }

    private void openReservation() {

        new ReservationFrame(loggedInUser);
    }

    private void openCancellation() {

        new CancellationFrame();
    }

    private void logout() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (result == JOptionPane.YES_OPTION) {

            dispose();

            new LoginFrame();
        }
    }
}