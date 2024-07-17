/**
 * This package has all the REST API endpoints for managing a library.
 */
package com.org.bansira.lbms.controller;

import com.org.bansira.lbms.domain.Book;
import com.org.bansira.lbms.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * APIs to Manage a Library with multiple departments
 */
@RestController
@RequestMapping("/api/books")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    /**
     * API endpoint to add a book to library
     * @param book the book to be added.
     * @return a saved book if saved successfully or returns an error message if not saved.
     */
    @PostMapping("/add")
    ResponseEntity<Object> addBook(@RequestBody Book book) {
        Optional<Book> response = libraryService.addBook(book);
        return response
                .<ResponseEntity<Object>>map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Book with ISBN " + book.getIsbn() + " already exists", HttpStatus.CONFLICT));
    }

    /**
     * API endpoint to fetch a book by its ISBN
     * @param isbn is the unique id of the book to be fetched.
     * @return the book with given ISBN or returns a book not found message.
     */
    @GetMapping("/{isbn}")
    ResponseEntity<Object> getBookByIsbn(@PathVariable String isbn) {
        Optional<Book> response = libraryService.findBookByIsbn(isbn);
        return response
                .<ResponseEntity<Object>>map(value -> new ResponseEntity<>(libraryService.findBookByIsbn(isbn), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Book with ISBN " + isbn + " not found", HttpStatus.OK));
    }

    /**
     * API endpoint to fetch all books registered in the library.
     * @return a list of books registered in the library.
     */
    @GetMapping
    ResponseEntity<Object> getAllBooks() {
        Optional<List<Book>> response = libraryService.listAllBooks();
        return response
                .<ResponseEntity<Object>>map(books -> new ResponseEntity<>(books, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("No Books Found", HttpStatus.OK));
    }

    /**
     * API endpoint to fetch all available books in the library.
     * @return a list of books currently available in the library.
     */
    @GetMapping("/available")
    ResponseEntity<Object> getAllAvailableBooks() {
        Optional<List<Book>> response = libraryService.listAvailableBooks();
        return response
                .<ResponseEntity<Object>>map(value -> new ResponseEntity<>(libraryService.listAvailableBooks(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("No Books Available", HttpStatus.OK));
    }

    /**
     * API endpoint to fetch all books matching a title provided.
     * @param title to match the books in the library.
     * @return a list of books matching the given title.
     */
    @GetMapping("/title/{title}")
    ResponseEntity<Object> getBookByTitle(@PathVariable String title) {
        Optional<List<Book>> response = libraryService.findBookByTitle(title);
        return response
                .<ResponseEntity<Object>>map(value -> new ResponseEntity<>(libraryService.findBookByTitle(title), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Book with Title " + title + "not found", HttpStatus.OK));
    }

    /**
     * API endpoint to fetch books by an author.
     * @param author of the books to be fetched from the library.
     * @return a list of books by the author provided.
     */
    @GetMapping("/author/{author}")
    ResponseEntity<Object> getBookByAuthor(@PathVariable String author) {
        Optional<List<Book>> response = libraryService.findBookByAuthor(author);
        return response
                .<ResponseEntity<Object>>map(value -> new ResponseEntity<>(libraryService.findBookByAuthor(author), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("No Books by Author " + author + "Found", HttpStatus.OK));
    }

    /**
     * API endpoint to remove a book from the library.
     * @param isbn is the unique id of the book to be removed from the library.
     * @return a count of books successfully removed from the library.
     */
    @DeleteMapping("/{isbn}")
    ResponseEntity<Long> removeBook(@PathVariable String isbn) {
        return new ResponseEntity<>(libraryService.removeBook(isbn), HttpStatus.OK);
    }
}
