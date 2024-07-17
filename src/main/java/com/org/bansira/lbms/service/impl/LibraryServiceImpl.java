package com.org.bansira.lbms.service.impl;

import com.org.bansira.lbms.data.BookRepository;
import com.org.bansira.lbms.domain.Book;
import com.org.bansira.lbms.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
