-- =========================================================
-- DIGITAL LIBRARY MANAGEMENT SYSTEM
-- Database: library_management
-- =========================================================

CREATE DATABASE library_management;

USE library_management;


-- =========================================================
-- USERS TABLE
-- =========================================================

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);


-- =========================================================
-- BOOKS TABLE
-- =========================================================

CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) NOT NULL UNIQUE,
    category VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    available_quantity INT NOT NULL
);


-- =========================================================
-- ISSUES TABLE
-- =========================================================

CREATE TABLE issues (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    issue_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(50) NOT NULL,

    CONSTRAINT fk_issue_book
        FOREIGN KEY (book_id)
        REFERENCES books(id),

    CONSTRAINT fk_issue_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);


-- =========================================================
-- BOOK RESERVATIONS TABLE
-- =========================================================

CREATE TABLE book_reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    reservation_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,

    CONSTRAINT fk_reservation_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_reservation_book
        FOREIGN KEY (book_id)
        REFERENCES books(id)
);


-- =========================================================
-- FINES TABLE
-- =========================================================

CREATE TABLE fines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    issue_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    amount DOUBLE NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,

    CONSTRAINT fk_fine_issue
        FOREIGN KEY (issue_id)
        REFERENCES issues(id),

    CONSTRAINT fk_fine_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);


-- =========================================================
-- QUERIES TABLE
-- =========================================================

CREATE TABLE queries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    subject VARCHAR(255) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    response VARCHAR(1000),
    status VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,

    CONSTRAINT fk_query_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);


-- =========================================================
-- NOTIFICATIONS TABLE
-- =========================================================

CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    message VARCHAR(500) NOT NULL,
    `read` BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL,

    CONSTRAINT fk_notification_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);


-- =========================================================
-- SAMPLE ADMIN USER
-- =========================================================

INSERT INTO users
    (username, name, email, password, role)
VALUES
    (
        'admin',
        'Admin',
        'admin@library.com',
        'admin123',
        'ADMIN'
    );


-- =========================================================
-- SAMPLE USER
-- =========================================================

INSERT INTO users
    (username, name, email, password, role)
VALUES
    (
        'user',
        'Demo User',
        'user@library.com',
        'user123',
        'USER'
    );


-- =========================================================
-- SAMPLE BOOKS
-- =========================================================

INSERT INTO books
    (title, author, isbn, category, quantity, available_quantity)
VALUES
    (
        'Clean Code',
        'Robert C. Martin',
        '9780132350884',
        'Programming',
        5,
        5
    ),
    (
        'The Pragmatic Programmer',
        'Andrew Hunt',
        '9780135957059',
        'Programming',
        4,
        4
    ),
    (
        'Database System Concepts',
        'Abraham Silberschatz',
        '9780078022159',
        'Database',
        3,
        3
    );


-- =========================================================
-- END OF DATABASE SCRIPT
-- =========================================================