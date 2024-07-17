package com.org.bansira.lbms.service;

import com.org.bansira.lbms.data.BookRepository;
import com.org.bansira.lbms.domain.Book;
import com.org.bansira.lbms.service.impl.LibraryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LibraryServiceImpl libraryService;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book("1","The Adventures of Sherlock Holmes", "978-1-56619-909-4", "Arthur Conan Doyle", "Mystery", 1892, "Literature", true);
    }

    @Test
    void testAddBook_Success() {
        when(bookRepository.existsByIsbn(anyString())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Optional<Book> result = libraryService.addBook(book);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
    }

    @Test
    void testAddBook_AlreadyExists() {
        when(bookRepository.existsByIsbn(anyString())).thenReturn(true);

        Optional<Book> result = libraryService.addBook(book);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindBookByIsbn() {
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(book));

        Optional<Book> result = libraryService.findBookByIsbn("978-1-56619-909-4");

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
    }

    @Test
    void testListAllBooks() {
        List<Book> books = Collections.singletonList(book);
        when(bookRepository.findAll()).thenReturn(books);

        Optional<List<Book>> result = libraryService.listAllBooks();

        assertTrue(result.isPresent());
        assertEquals(books, result.get());
    }

    @Test
    void testListAvailableBooks() {
        List<Book> books = Collections.singletonList(book);
        when(bookRepository.findByIsAvailable(true)).thenReturn(Optional.of(books));

        Optional<List<Book>> result = libraryService.listAvailableBooks();

        assertTrue(result.isPresent());
        assertEquals(books, result.get());
    }

    @Test
    void testFindBookByTitle() {
        List<Book> books = Collections.singletonList(book);
        when(bookRepository.findByTitle(anyString())).thenReturn(Optional.of(books));

        Optional<List<Book>> result = libraryService.findBookByTitle("The Adventures of Sherlock Holmes");

        assertTrue(result.isPresent());
        assertEquals(books, result.get());
    }

    @Test
    void testFindBookByAuthor() {
        List<Book> books = Collections.singletonList(book);
        when(bookRepository.findByAuthor(anyString())).thenReturn(Optional.of(books));

        Optional<List<Book>> result = libraryService.findBookByAuthor("Arthur Conan Doyle");

        assertTrue(result.isPresent());
        assertEquals(books, result.get());
    }
}
