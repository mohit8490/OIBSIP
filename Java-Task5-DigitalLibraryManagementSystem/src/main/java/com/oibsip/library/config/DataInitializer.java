package com.oibsip.library.config;

import com.oibsip.library.model.Book;
import com.oibsip.library.model.User;
import com.oibsip.library.repository.BookRepository;
import com.oibsip.library.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeData(
            UserRepository userRepository,
            BookRepository bookRepository) {

        return args -> {

            // ==========================================
            // ADMIN USER
            // ==========================================

            if (!userRepository.existsByUsername("admin")) {

                User admin = new User();

                admin.setName("Library Administrator");
                admin.setEmail("admin@library.com");
                admin.setUsername("admin");
                admin.setPassword("admin123");
                admin.setRole("ADMIN");

                userRepository.save(admin);

                System.out.println("Default admin user created.");
            }


            // ==========================================
            // DEMO USER
            // ==========================================

            if (!userRepository.existsByUsername("user")) {

                User user = new User();

                user.setName("Demo User");
                user.setEmail("user@library.com");
                user.setUsername("user");
                user.setPassword("user123");
                user.setRole("USER");

                userRepository.save(user);

                System.out.println("Default demo user created.");
            }


            // ==========================================
            // SAMPLE BOOK 1
            // ==========================================

            createBookIfNotExists(
                    bookRepository,
                    "Clean Code",
                    "Robert C. Martin",
                    "9780132350884",
                    "Programming",
                    5
            );


            // ==========================================
            // SAMPLE BOOK 2
            // ==========================================

            createBookIfNotExists(
                    bookRepository,
                    "Effective Java",
                    "Joshua Bloch",
                    "9780134685991",
                    "Programming",
                    4
            );


            // ==========================================
            // SAMPLE BOOK 3
            // ==========================================

            createBookIfNotExists(
                    bookRepository,
                    "The Alchemist",
                    "Paulo Coelho",
                    "9780061122415",
                    "Fiction",
                    6
            );


            // ==========================================
            // SAMPLE BOOK 4
            // ==========================================

            createBookIfNotExists(
                    bookRepository,
                    "Introduction to Algorithms",
                    "Thomas H. Cormen",
                    "9780262046305",
                    "Computer Science",
                    3
            );


            // ==========================================
            // SAMPLE BOOK 5
            // ==========================================

            createBookIfNotExists(
                    bookRepository,
                    "Database System Concepts",
                    "Abraham Silberschatz",
                    "9780073523323",
                    "Database",
                    4
            );


            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "Digital Library initial data check completed."
            );

            System.out.println(
                    "Admin Username: admin"
            );

            System.out.println(
                    "Demo User Username: user"
            );

            System.out.println(
                    "=========================================="
            );
        };
    }


    // ==========================================
    // CREATE BOOK IF ISBN DOES NOT EXIST
    // ==========================================

    private void createBookIfNotExists(
            BookRepository bookRepository,
            String title,
            String author,
            String isbn,
            String category,
            int quantity) {

        if (!bookRepository.existsByIsbn(isbn)) {

            Book book = new Book();

            book.setTitle(title);
            book.setAuthor(author);
            book.setIsbn(isbn);
            book.setCategory(category);
            book.setQuantity(quantity);
            book.setAvailableQuantity(quantity);

            bookRepository.save(book);

            System.out.println(
                    "Book added: " + title
            );
        }
    }
}