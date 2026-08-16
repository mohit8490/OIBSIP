package com.oibsip.reservation.ui;

import com.oibsip.reservation.dao.ReservationDAO;
import com.oibsip.reservation.model.Reservation;

import javax.swing.*;
import java.awt.*;

public class CancellationFrame extends JFrame {

    private JTextField pnrField;

    private JLabel passengerLabel;
    private JLabel trainLabel;
    private JLabel dateLabel;
    private JLabel routeLabel;
    private JLabel classLabel;

    private ReservationDAO reservationDAO;

    public CancellationFrame() {

        reservationDAO = new ReservationDAO();

        setTitle("Reservation Search / Cancellation");
        setSize(600, 500);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        setVisible(true);
    }

    private void createUI() {

        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel = new JPanel(
                new BorderLayout(10, 10)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 30, 20, 30
                )
        );

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel = new JLabel(
                "SEARCH / CANCEL RESERVATION",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // =========================
        // SEARCH PANEL
        // =========================

        JPanel searchPanel = new JPanel(
                new FlowLayout()
        );

        JLabel pnrLabel =
                new JLabel("Enter PNR:");

        pnrField = new JTextField(15);

        JButton searchButton =
                new JButton("SEARCH");

        searchButton.addActionListener(
                e -> searchReservation()
        );

        searchPanel.add(pnrLabel);
        searchPanel.add(pnrField);
        searchPanel.add(searchButton);

        // =========================
        // DETAILS PANEL
        // =========================

        JPanel detailsPanel = new JPanel(
                new GridLayout(5, 2, 10, 15)
        );

        // Passenger

        detailsPanel.add(
                new JLabel("Passenger:")
        );

        passengerLabel =
                new JLabel("-");

        detailsPanel.add(
                passengerLabel
        );

        // Train

        detailsPanel.add(
                new JLabel("Train:")
        );

        trainLabel =
                new JLabel("-");

        detailsPanel.add(
                trainLabel
        );

        // Class

        detailsPanel.add(
                new JLabel("Class:")
        );

        classLabel =
                new JLabel("-");

        detailsPanel.add(
                classLabel
        );

        // Journey Date

        detailsPanel.add(
                new JLabel("Journey Date:")
        );

        dateLabel =
                new JLabel("-");

        detailsPanel.add(
                dateLabel
        );

        // Route

        detailsPanel.add(
                new JLabel("Route:")
        );

        routeLabel =
                new JLabel("-");

        detailsPanel.add(
                routeLabel
        );

        // =========================
        // CENTER PANEL
        // =========================

        JPanel centerPanel = new JPanel(
                new BorderLayout(10, 20)
        );

        centerPanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                detailsPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON PANEL
        // =========================

        JButton cancelButton =
                new JButton(
                        "CANCEL RESERVATION"
                );

        cancelButton.addActionListener(
                e -> cancelReservation()
        );

        JButton closeButton =
                new JButton("CLOSE");

        closeButton.addActionListener(
                e -> dispose()
        );

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout()
                );

        bottomPanel.add(cancelButton);
        bottomPanel.add(closeButton);

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // Add main panel to frame

        add(mainPanel);
    }

    // =========================
    // SEARCH RESERVATION
    // =========================

    private void searchReservation() {

        String pnrText =
                pnrField.getText().trim();

        // Check empty PNR

        if (pnrText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter PNR.",
                    "Missing PNR",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            long pnr =
                    Long.parseLong(pnrText);

            Reservation reservation =
                    reservationDAO
                            .getReservationByPNR(pnr);

            if (reservation != null) {

                // Passenger

                passengerLabel.setText(
                        reservation.getPassengerName()
                );

                // Train

                trainLabel.setText(
                        reservation.getTrainNumber()
                        + " - "
                        + reservation.getTrainName()
                );

                // Class

                classLabel.setText(
                        reservation.getClassType()
                );

                // Journey Date

                dateLabel.setText(
                        reservation.getJourneyDate()
                                .toString()
                );

                // Route

                routeLabel.setText(
                        reservation.getSourceStation()
                        + " → "
                        + reservation.getDestinationStation()
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No reservation found for this PNR.",
                        "Reservation Not Found",
                        JOptionPane.WARNING_MESSAGE
                );

                clearReservationDetails();
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "PNR must contain numbers only.",
                    "Invalid PNR",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // CANCEL RESERVATION
    // =========================

    private void cancelReservation() {

        String pnrText =
                pnrField.getText().trim();

        // Check empty PNR

        if (pnrText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter PNR.",
                    "Missing PNR",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            long pnr =
                    Long.parseLong(pnrText);

            // First check whether reservation exists

            Reservation reservation =
                    reservationDAO
                            .getReservationByPNR(pnr);

            if (reservation == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "No reservation found for this PNR.",
                        "Reservation Not Found",
                        JOptionPane.WARNING_MESSAGE
                );

                clearReservationDetails();

                return;
            }

            // Confirmation

            int confirmation =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to cancel "
                            + "this reservation?\n\n"
                            + "Passenger: "
                            + reservation.getPassengerName()
                            + "\nPNR: "
                            + pnr,
                            "Confirm Cancellation",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (confirmation !=
                    JOptionPane.YES_OPTION) {

                return;
            }

            // Cancel from database

            boolean cancelled =
                    reservationDAO
                            .cancelReservation(pnr);

            if (cancelled) {

                JOptionPane.showMessageDialog(
                        this,
                        "Reservation cancelled successfully.",
                        "Cancellation Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearReservationDetails();

                pnrField.setText("");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Cancellation failed.",
                        "Cancellation Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid PNR.\n"
                    + "PNR must contain numbers only.",
                    "Invalid PNR",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // CLEAR DETAILS
    // =========================

    private void clearReservationDetails() {

        passengerLabel.setText("-");
        trainLabel.setText("-");
        classLabel.setText("-");
        dateLabel.setText("-");
        routeLabel.setText("-");
    }
}