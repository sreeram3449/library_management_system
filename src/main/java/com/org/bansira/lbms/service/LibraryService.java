package com.org.bansira.lbms.service;

import com.org.bansira.lbms.domain.Book;

import java.util.Optional;

public interface LibraryService {
    Optional<Book> addBook(Book book);

    Optional<Book> findBookByIsbn(String isbn);
}
