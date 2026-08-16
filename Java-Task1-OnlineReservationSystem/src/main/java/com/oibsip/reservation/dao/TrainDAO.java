package com.oibsip.reservation.dao;

import com.oibsip.reservation.database.DatabaseConnection;
import com.oibsip.reservation.model.Train;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainDAO {

    // Get all trains
    public List<Train> getAllTrains() {

        List<Train> trains = new ArrayList<>();

        String sql = "SELECT * FROM trains";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Train train = new Train();

                train.setTrainNumber(
                        resultSet.getInt("train_number")
                );

                train.setTrainName(
                        resultSet.getString("train_name")
                );

                trains.add(train);
            }

        } catch (SQLException e) {

            System.out.println("Error while fetching trains!");
            e.printStackTrace();
        }

        return trains;
    }

    // Find a train by train number
    public Train getTrainByNumber(int trainNumber) {

        String sql =
                "SELECT * FROM trains WHERE train_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, trainNumber);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Train train = new Train();

                train.setTrainNumber(
                        resultSet.getInt("train_number")
                );

                train.setTrainName(
                        resultSet.getString("train_name")
                );

                return train;
            }

        } catch (SQLException e) {

            System.out.println("Error while searching train!");
            e.printStackTrace();
        }

        return null;
    }
}