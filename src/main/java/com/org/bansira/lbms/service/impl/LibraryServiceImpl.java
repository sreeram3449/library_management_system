package com.org.bansira.lbms.service.impl;

import com.org.bansira.lbms.data.BookRepository;
import com.org.bansira.lbms.domain.Book;
import com.org.bansira.lbms.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    BookRepository bookRepository;

    /**
     * @param book
     * @return
     */
    @Override
    public Optional<Book> addBook(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            return Optional.empty();
        }
        return Optional.of(bookRepository.save(book));
    }

    /**
     * @param isbn
     * @return
     */
    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    /**
     * @return
     */
    @Override
    public Optional<List<Book>> listAllBooks() {
        return Optional.of(bookRepository.findAll());
    }

    /**
     * @return
     */
    @Override
    public Optional<List<Book>> listAvailableBooks() {
        return bookRepository.findByIsAvailable(Boolean.TRUE);
    }

    /**
     * @param title
     * @return
     */
    @Override
    public Optional<List<Book>> findBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
}
