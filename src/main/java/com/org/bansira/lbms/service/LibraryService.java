/**
 * This package contains service layer implementations of Library APIs.
 */
package com.org.bansira.lbms.service;

import com.org.bansira.lbms.domain.Book;

import java.util.List;
import java.util.Optional;

public interface LibraryService {
    Optional<Book> addBook(Book book);

    Optional<Book> findBookByIsbn(String isbn);

    Optional<List<Book>> listAllBooks();

    Optional<List<Book>> listAvailableBooks();

    Optional<List<Book>> findBookByTitle(String title);

    Optional<List<Book>> findBookByAuthor(String author);

    Long removeBook(String isbn);
}
