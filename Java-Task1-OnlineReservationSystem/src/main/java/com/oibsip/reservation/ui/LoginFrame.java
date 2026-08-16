package com.oibsip.reservation.ui;

import com.oibsip.reservation.dao.UserDAO;
import com.oibsip.reservation.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private UserDAO userDAO;

    public LoginFrame() {

        userDAO = new UserDAO();

        setTitle("Online Reservation System - Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createLoginUI();

        setVisible(true);
    }

    private void createLoginUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "ONLINE RESERVATION SYSTEM",
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

        JPanel formPanel = new JPanel(
                new GridLayout(2, 2, 10, 15)
        );

        JLabel usernameLabel =
                new JLabel("Username:");

        usernameField =
                new JTextField();

        JLabel passwordLabel =
                new JLabel("Password:");

        passwordField =
                new JPasswordField();

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        JButton loginButton =
                new JButton("LOGIN");

        loginButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        loginButton.addActionListener(
                e -> loginUser()
        );

        mainPanel.add(
                loginButton,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }

    private void loginUser() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        User user =
                userDAO.login(
                        username,
                        password
                );

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            // ReservationFrame will be added next
            new DashboardFrame(user);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}