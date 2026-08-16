package com.oibsip.reservation;

import com.oibsip.reservation.ui.LoginFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new LoginFrame()
        );
    }
}