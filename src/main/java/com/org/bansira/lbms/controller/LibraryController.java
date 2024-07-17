package com.org.bansira.lbms.controller;

import com.org.bansira.lbms.domain.Book;
import com.org.bansira.lbms.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/add")
    ResponseEntity<Object> addBook(@RequestBody Book book) {
        Optional<Book> response = libraryService.addBook(book);
        return response
                .<ResponseEntity<Object>>map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Book with ISBN " + book.getIsbn() + " already exists", HttpStatus.CONFLICT));
    }

    @GetMapping("/{isbn}")
    ResponseEntity<Object> getBookByIsbn(@PathVariable String isbn) {
        Optional<Book> response = libraryService.findBookByIsbn(isbn);
        return response
                .<ResponseEntity<Object>>map(value -> new ResponseEntity<>(libraryService.findBookByIsbn(isbn), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Book with ISBN " + isbn + " not found", HttpStatus.OK));
    }
}
