package com.oibsip.library.service;

import com.oibsip.library.model.Book;
import com.oibsip.library.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;


    public BookService(
            BookRepository bookRepository) {

        this.bookRepository = bookRepository;
    }


    // =====================================================
    // ADD BOOK
    // =====================================================

    public Book addBook(Book book) {

        if (book == null) {

            throw new RuntimeException(
                    "Book cannot be null."
            );
        }


        if (book.getTitle() == null ||
                book.getTitle().isBlank()) {

            throw new RuntimeException(
                    "Book title is required."
            );
        }


        if (book.getAuthor() == null ||
                book.getAuthor().isBlank()) {

            throw new RuntimeException(
                    "Author is required."
            );
        }


        if (book.getIsbn() == null ||
                book.getIsbn().isBlank()) {

            throw new RuntimeException(
                    "ISBN is required."
            );
        }


        if (book.getCategory() == null ||
                book.getCategory().isBlank()) {

            throw new RuntimeException(
                    "Category is required."
            );
        }


        if (bookRepository.existsByIsbn(
                book.getIsbn())) {

            throw new RuntimeException(
                    "A book with this ISBN already exists."
            );
        }


        if (book.getQuantity() <= 0) {

            throw new RuntimeException(
                    "Quantity must be greater than 0."
            );
        }


        // New book means all copies are available

        book.setAvailableQuantity(
                book.getQuantity()
        );


        return bookRepository.save(book);
    }


    // =====================================================
    // UPDATE BOOK
    // =====================================================

    public Book updateBook(
            Long id,
            Book updatedBook) {

        Book existingBook =
                bookRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Book not found."
                                )
                        );


        if (updatedBook == null) {

            throw new RuntimeException(
                    "Book data cannot be null."
            );
        }


        if (updatedBook.getTitle() == null ||
                updatedBook.getTitle().isBlank()) {

            throw new RuntimeException(
                    "Book title is required."
            );
        }


        if (updatedBook.getAuthor() == null ||
                updatedBook.getAuthor().isBlank()) {

            throw new RuntimeException(
                    "Author is required."
            );
        }


        if (updatedBook.getIsbn() == null ||
                updatedBook.getIsbn().isBlank()) {

            throw new RuntimeException(
                    "ISBN is required."
            );
        }


        if (updatedBook.getCategory() == null ||
                updatedBook.getCategory().isBlank()) {

            throw new RuntimeException(
                    "Category is required."
            );
        }


        int issuedBooks =
                existingBook.getQuantity()
                        - existingBook.getAvailableQuantity();


        int newQuantity =
                updatedBook.getQuantity();


        if (newQuantity <= 0) {

            throw new RuntimeException(
                    "Quantity must be greater than 0."
            );
        }


        if (newQuantity < issuedBooks) {

            throw new RuntimeException(
                    "Quantity cannot be less than currently issued books."
            );
        }


        // Check ISBN only if it is changed

        if (!existingBook.getIsbn()
                .equals(updatedBook.getIsbn())
                && bookRepository.existsByIsbn(
                        updatedBook.getIsbn())) {

            throw new RuntimeException(
                    "A book with this ISBN already exists."
            );
        }


        existingBook.setTitle(
                updatedBook.getTitle()
        );


        existingBook.setAuthor(
                updatedBook.getAuthor()
        );


        existingBook.setIsbn(
                updatedBook.getIsbn()
        );


        existingBook.setCategory(
                updatedBook.getCategory()
        );


        existingBook.setQuantity(
                newQuantity
        );


        /*
         * Available copies are calculated from
         * total copies minus currently issued copies.
         */

        existingBook.setAvailableQuantity(
                newQuantity - issuedBooks
        );


        return bookRepository.save(
                existingBook
        );
    }


    // =====================================================
    // DELETE BOOK
    // =====================================================

    public void deleteBook(Long id) {

        if (!bookRepository.existsById(id)) {

            throw new RuntimeException(
                    "Book not found."
            );
        }


        bookRepository.deleteById(id);
    }


    // =====================================================
    // GET BOOK BY ID
    // =====================================================

    public Optional<Book> getBookById(
            Long id) {

        return bookRepository.findById(id);
    }


    // =====================================================
    // GET ALL BOOKS
    // =====================================================

    public List<Book> getAllBooks() {

        return bookRepository.findAll();
    }


    // =====================================================
    // SEARCH BOOKS
    // =====================================================

    public List<Book> searchBooks(
            String keyword) {

        if (keyword == null ||
                keyword.isBlank()) {

            return getAllBooks();
        }


        return bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }


    // =====================================================
    // GET BOOKS BY CATEGORY
    // =====================================================

    public List<Book> getBooksByCategory(
            String category) {

        if (category == null ||
                category.isBlank()) {

            return getAllBooks();
        }


        return bookRepository
                .findByCategoryIgnoreCase(
                        category
                );
    }


    // =====================================================
    // CHECK BOOK AVAILABILITY
    // =====================================================

    public boolean isAvailable(
            Long bookId) {

        Book book =
                bookRepository.findById(bookId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Book not found."
                                )
                        );


        return book.getAvailableQuantity() > 0;
    }


    // =====================================================
    // DECREASE AVAILABLE QUANTITY
    // =====================================================

    public void decreaseAvailableQuantity(
            Book book) {

        if (book == null) {

            throw new RuntimeException(
                    "Book cannot be null."
            );
        }


        if (book.getAvailableQuantity() <= 0) {

            throw new RuntimeException(
                    "Book is not available."
            );
        }


        book.setAvailableQuantity(
                book.getAvailableQuantity() - 1
        );


        bookRepository.save(book);
    }


    // =====================================================
    // INCREASE AVAILABLE QUANTITY
    // =====================================================

    public void increaseAvailableQuantity(
            Book book) {

        if (book == null) {

            throw new RuntimeException(
                    "Book cannot be null."
            );
        }


        if (book.getAvailableQuantity()
                < book.getQuantity()) {

            book.setAvailableQuantity(
                    book.getAvailableQuantity() + 1
            );


            bookRepository.save(book);
        }
    }
}