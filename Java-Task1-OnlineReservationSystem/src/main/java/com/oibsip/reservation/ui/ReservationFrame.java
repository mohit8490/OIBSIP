package com.oibsip.reservation.ui;

import com.oibsip.reservation.dao.ReservationDAO;
import com.oibsip.reservation.dao.TrainDAO;
import com.oibsip.reservation.model.Reservation;
import com.oibsip.reservation.model.Train;
import com.oibsip.reservation.model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class ReservationFrame extends JFrame {

    private JTextField passengerNameField;
    private JComboBox<String> trainComboBox;
    private JComboBox<String> classComboBox;
    private JTextField journeyDateField;
    private JTextField sourceField;
    private JTextField destinationField;

    private TrainDAO trainDAO;
    private ReservationDAO reservationDAO;

    private List<Train> trains;

    private User loggedInUser;

    public ReservationFrame(User user) {

        this.loggedInUser = user;

        trainDAO = new TrainDAO();
        reservationDAO = new ReservationDAO();

        setTitle("Online Reservation System - Reservation");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createReservationUI();
        loadTrains();

        setVisible(true);
    }

    private void createReservationUI() {

        JPanel mainPanel = new JPanel(
                new BorderLayout(10, 10)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 30, 20, 30
                )
        );

        JLabel titleLabel = new JLabel(
                "TRAIN RESERVATION",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        JPanel formPanel = new JPanel(
                new GridLayout(6, 2, 10, 15)
        );

        // Passenger name
        formPanel.add(
                new JLabel("Passenger Name:")
        );

        passengerNameField =
                new JTextField();

        formPanel.add(
                passengerNameField
        );

        // Train
        formPanel.add(
                new JLabel("Select Train:")
        );

        trainComboBox =
                new JComboBox<>();

        formPanel.add(
                trainComboBox
        );

        // Class
        formPanel.add(
                new JLabel("Class:")
        );

        classComboBox =
                new JComboBox<>(
                        new String[]{
                                "AC First Class",
                                "AC 2 Tier",
                                "AC 3 Tier",
                                "Sleeper",
                                "AC Chair Car"
                        }
                );

        formPanel.add(
                classComboBox
        );

        // Journey date
        formPanel.add(
                new JLabel("Journey Date:")
        );

        journeyDateField =
                new JTextField();

        journeyDateField.setToolTipText(
                "Format: YYYY-MM-DD"
        );

        formPanel.add(
                journeyDateField
        );

        // Source
        formPanel.add(
                new JLabel("Source Station:")
        );

        sourceField =
                new JTextField();

        formPanel.add(
                sourceField
        );

        // Destination
        formPanel.add(
                new JLabel("Destination Station:")
        );

        destinationField =
                new JTextField();

        formPanel.add(
                destinationField
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        JButton bookButton =
                new JButton("BOOK RESERVATION");

        bookButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        bookButton.addActionListener(
                e -> bookReservation()
        );

        JButton cancelButton =
                new JButton("CANCEL / SEARCH");
        
        JButton backButton =
                new JButton("BACK TO DASHBOARD");

        backButton.addActionListener(
                e -> {
                    dispose();
                    new DashboardFrame(loggedInUser);
                }
        );

        cancelButton.addActionListener(
                e -> openCancellationFrame()
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout()
                );

        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }

    private void loadTrains() {

        trains = trainDAO.getAllTrains();

        for (Train train : trains) {

            trainComboBox.addItem(
                    train.getTrainNumber()
                    + " - "
                    + train.getTrainName()
            );
        }
    }

    private void bookReservation() {

        String passengerName =
                passengerNameField.getText().trim();

        String journeyDateText =
                journeyDateField.getText().trim();

        String source =
                sourceField.getText().trim();

        String destination =
                destinationField.getText().trim();

        if (passengerName.isEmpty()
                || journeyDateText.isEmpty()
                || source.isEmpty()
                || destination.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            Date journeyDate =
                    Date.valueOf(journeyDateText);

            int selectedIndex =
                    trainComboBox.getSelectedIndex();

            if (selectedIndex < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a train."
                );

                return;
            }

            Train selectedTrain =
                    trains.get(selectedIndex);

            String classType =
                    (String) classComboBox.getSelectedItem();

            Reservation reservation =
                    new Reservation();

            reservation.setPassengerName(
                    passengerName
            );

            reservation.setTrainNumber(
                    selectedTrain.getTrainNumber()
            );

            reservation.setTrainName(
                    selectedTrain.getTrainName()
            );

            reservation.setClassType(
                    classType
            );

            reservation.setJourneyDate(
                    journeyDate
            );

            reservation.setSourceStation(
                    source
            );

            reservation.setDestinationStation(
                    destination
            );

            long pnr =
                    reservationDAO.bookReservation(
                            reservation
                    );

            if (pnr != -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Reservation Successful!\n\n"
                        + "PNR: " + pnr
                        + "\nPassenger: "
                        + passengerName
                        + "\nTrain: "
                        + selectedTrain.getTrainName()
                        + "\nJourney Date: "
                        + journeyDate,
                        "Booking Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Reservation failed.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date format.\n"
                    + "Please use YYYY-MM-DD.",
                    "Invalid Date",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void openCancellationFrame() {

        new CancellationFrame();
    }

    private void clearFields() {

        passengerNameField.setText("");
        journeyDateField.setText("");
        sourceField.setText("");
        destinationField.setText("");

        if (trainComboBox.getItemCount() > 0) {
            trainComboBox.setSelectedIndex(0);
        }

        classComboBox.setSelectedIndex(0);
    }
}