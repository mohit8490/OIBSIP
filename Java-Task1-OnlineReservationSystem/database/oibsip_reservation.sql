-- ============================================
-- OIBSIP Online Reservation System
-- Database Setup
-- ============================================

CREATE DATABASE IF NOT EXISTS oibsip_reservation;

USE oibsip_reservation;

-- ============================================
-- USERS TABLE
-- ============================================

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Default login account
INSERT INTO users (username, password)
VALUES ('admin', 'admin123');

-- ============================================
-- TRAINS TABLE
-- ============================================

CREATE TABLE IF NOT EXISTS trains (
    train_number INT PRIMARY KEY,
    train_name VARCHAR(100) NOT NULL
);

INSERT INTO trains (train_number, train_name)
VALUES
(12001, 'Bhopal Shatabdi Express'),
(12155, 'Bhopal Express'),
(12627, 'Karnataka Express'),
(12923, 'Mumbai Central Tejas Express'),
(11057, 'Mumbai Amritsar Express');

-- ============================================
-- RESERVATIONS TABLE
-- ============================================

CREATE TABLE IF NOT EXISTS reservations (
    pnr BIGINT PRIMARY KEY,
    passenger_name VARCHAR(100) NOT NULL,
    train_number INT NOT NULL,
    train_name VARCHAR(100) NOT NULL,
    class_type VARCHAR(30) NOT NULL,
    journey_date DATE NOT NULL,
    source_station VARCHAR(100) NOT NULL,
    destination_station VARCHAR(100) NOT NULL,

    FOREIGN KEY (train_number)
        REFERENCES trains(train_number)
);

-- ============================================
-- VERIFY DATABASE
-- ============================================

SHOW TABLES;

SELECT * FROM users;

SELECT * FROM trains;

SELECT * FROM reservations;