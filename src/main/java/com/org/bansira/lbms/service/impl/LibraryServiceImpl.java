/**
 * This package contains service layer implementations of Library APIs.
 */
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

    /** Adds a book to the library.
     * @param book the book to be added.
     * @return a saved book if saved successfully or returns an empty Optional object.
     */
    @Override
    public Optional<Book> addBook(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            return Optional.empty();
        }
        return Optional.of(bookRepository.save(book));
    }

    /** Fetches a Book by ISBN.
     * @param isbn is the unique id of the book to be fetched.
     * @returnthe book with given ISBN or null object wrapped in Optional object.
     */
    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    /** Fetches all registered books in the library.
     * @return a list of books registered in the library.
     */
    @Override
    public Optional<List<Book>> listAllBooks() {
        return Optional.of(bookRepository.findAll());
    }

    /** Fetches all books that are currently available in the library.
     * @return a list of books currently available in the library.
     */
    @Override
    public Optional<List<Book>> listAvailableBooks() {
        return bookRepository.findByIsAvailable(Boolean.TRUE);
    }

    /** Fetches all books matching a title provided.
     * @param title to match the books in the library.
     * @return a list of books matching the given title wrapped in Optional object.
     */
    @Override
    public Optional<List<Book>> findBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    /** Fetches all books by author.
     * @param author of the books to be fetched from the library.
     * @return a list of books by the author provided.
     */
    @Override
    public Optional<List<Book>> findBookByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    /** Removes a book by ISBN provided.
     * @param isbn is the unique id of the book to be removed from the library.
     * @return a count of books successfully removed from the library.
     */
    @Override
    public Long removeBook(String isbn) {
        return bookRepository.deleteByIsbn(isbn);
    }
}
