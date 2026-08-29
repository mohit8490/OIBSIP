package com.oibsip.library.service;

import com.oibsip.library.model.User;
import com.oibsip.library.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    // =====================================================
    // REGISTER USER
    // =====================================================

    public User registerUser(User user) {

        // Check username
        if (user.getUsername() == null
                || user.getUsername().isBlank()) {

            throw new RuntimeException(
                    "Username is required."
            );
        }


        // Check email
        if (user.getEmail() == null
                || user.getEmail().isBlank()) {

            throw new RuntimeException(
                    "Email is required."
            );
        }


        // Check password
        if (user.getPassword() == null
                || user.getPassword().isBlank()) {

            throw new RuntimeException(
                    "Password is required."
            );
        }


        // Check duplicate username
        if (userRepository.existsByUsername(
                user.getUsername())) {

            throw new RuntimeException(
                    "Username already exists."
            );
        }


        // Check duplicate email
        if (userRepository.existsByEmail(
                user.getEmail())) {

            throw new RuntimeException(
                    "Email already exists."
            );
        }


        // Default role
        if (user.getRole() == null
                || user.getRole().isBlank()) {

            user.setRole("USER");
        }


        return userRepository.save(user);
    }


    // =====================================================
    // LOGIN
    // USER CAN LOGIN USING USERNAME OR EMAIL
    // =====================================================

    public Optional<User> login(
            String usernameOrEmail,
            String password) {

        if (usernameOrEmail == null
                || usernameOrEmail.isBlank()
                || password == null
                || password.isBlank()) {

            return Optional.empty();
        }


        Optional<User> user =
                userRepository.findByUsername(
                        usernameOrEmail
                );


        // If username was not found,
        // try email
        if (user.isEmpty()) {

            user =
                    userRepository.findByEmail(
                            usernameOrEmail
                    );
        }


        // Check password
        if (user.isPresent()
                && user.get()
                        .getPassword()
                        .equals(password)) {

            return user;
        }


        return Optional.empty();
    }


    // =====================================================
    // GET USER BY ID
    // =====================================================

    public Optional<User> getUserById(Long id) {

        return userRepository.findById(id);
    }


    // =====================================================
    // GET USER BY USERNAME
    // =====================================================

    public Optional<User> getUserByUsername(
            String username) {

        return userRepository.findByUsername(
                username
        );
    }


    // =====================================================
    // GET ALL USERS
    // =====================================================

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }


    // =====================================================
    // GET MEMBERS
    // =====================================================

    public List<User> getMembers() {

        return userRepository.findAll()
                .stream()
                .filter(user ->
                        "USER".equalsIgnoreCase(
                                user.getRole()
                        )
                )
                .toList();
    }


    // =====================================================
    // DELETE USER
    // =====================================================

    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }
}