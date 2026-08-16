package com.oibsip.reservation.dao;

import com.oibsip.reservation.database.DatabaseConnection;
import com.oibsip.reservation.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Random;

public class ReservationDAO {

    // Generate a random 10-digit PNR
    private long generatePNR() {

        Random random = new Random();

        return 1000000000L +
                (long) (random.nextDouble() * 900000000L);
    }

    // Book a reservation
    public long bookReservation(Reservation reservation) {

        long pnr = generatePNR();

        String sql = """
                INSERT INTO reservations
                (pnr, passenger_name, train_number, train_name,
                 class_type, journey_date, source_station,
                 destination_station)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, pnr);
            statement.setString(2, reservation.getPassengerName());
            statement.setInt(3, reservation.getTrainNumber());
            statement.setString(4, reservation.getTrainName());
            statement.setString(5, reservation.getClassType());
            statement.setDate(6, reservation.getJourneyDate());
            statement.setString(7, reservation.getSourceStation());
            statement.setString(8, reservation.getDestinationStation());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                reservation.setPnr(pnr);

                return pnr;
            }

        } catch (SQLException e) {

            System.out.println("Error while booking reservation!");
            e.printStackTrace();
        }

        return -1;
    }

    // Find reservation using PNR
    public Reservation getReservationByPNR(long pnr) {

        String sql =
                "SELECT * FROM reservations WHERE pnr = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, pnr);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Reservation reservation = new Reservation();

                reservation.setPnr(
                        resultSet.getLong("pnr")
                );

                reservation.setPassengerName(
                        resultSet.getString("passenger_name")
                );

                reservation.setTrainNumber(
                        resultSet.getInt("train_number")
                );

                reservation.setTrainName(
                        resultSet.getString("train_name")
                );

                reservation.setClassType(
                        resultSet.getString("class_type")
                );

                reservation.setJourneyDate(
                        resultSet.getDate("journey_date")
                );

                reservation.setSourceStation(
                        resultSet.getString("source_station")
                );

                reservation.setDestinationStation(
                        resultSet.getString("destination_station")
                );

                return reservation;
            }

        } catch (SQLException e) {

            System.out.println("Error while searching reservation!");
            e.printStackTrace();
        }

        return null;
    }

    // Cancel reservation
    public boolean cancelReservation(long pnr) {

        String sql =
                "DELETE FROM reservations WHERE pnr = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, pnr);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {

            System.out.println("Error while cancelling reservation!");
            e.printStackTrace();
        }

        return false;
    }
}