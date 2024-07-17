/**
 * This package contains unit tests for Library API endpoints.
 */
package com.org.bansira.lbms.controller;

import com.org.bansira.lbms.domain.Book;
import com.org.bansira.lbms.security.TestSecurityConfig;
import com.org.bansira.lbms.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(LibraryController.class)
@Import(TestSecurityConfig.class)
public class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    private Book book1;
    private Book book2;

    /**
     * Dummy Book objects for running tests.
     */
    @BeforeEach
    public void setUp() {
        book1 = new Book("The Adventures of Sherlock Holmes", "978-1-56619-909-4", "Arthur Conan Doyle", "Mystery", 1892, "Literature", true);
        book2 = new Book("1984", "978-0-45228-425-0", "George Orwell", "Dystopian", 1949, "Literature", true);
    }

    @Test
    @WithMockUser
    @DisplayName("Add Book to Library Success Scenario")
    public void testAddBook_Success() throws Exception {
        when(libraryService.addBook(any(Book.class))).thenReturn(Optional.of(book1));

        mockMvc.perform(post("/api/books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"isbn\": \"978-1-56619-909-4\", \"title\": \"The Adventures of Sherlock Holmes\", \"author\": \"Arthur Conan Doyle\", \"genre\": \"Mystery\", \"publicationYear\": 1892, \"department\": \"Literature\", \"isAvailable\": true }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(book1.getIsbn()));
    }

    @Test
    @WithMockUser
    @DisplayName("Add Book to Library Failure Scenario")
    public void testAddBook_Conflict() throws Exception {
        when(libraryService.addBook(any(Book.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"isbn\": \"978-1-56619-909-4\", \"title\": \"The Adventures of Sherlock Holmes\", \"author\": \"Arthur Conan Doyle\", \"genre\": \"Mystery\", \"publicationYear\": 1892, \"department\": \"Literature\", \"isAvailable\": true }"))
                .andExpect(status().isConflict())
                .andExpect(content().string("Book with ISBN 978-1-56619-909-4 already exists"));
    }

    @Test
    @WithMockUser
    @DisplayName("Get Book by ISBN Success Scenario")
    public void testGetBookByIsbn_Found() throws Exception {
        when(libraryService.findBookByIsbn("978-1-56619-909-4")).thenReturn(Optional.of(book1));

        mockMvc.perform(get("/api/books/978-1-56619-909-4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(book1.getIsbn()));
    }

    @Test
    @WithMockUser
    @DisplayName("Get Book by ISBN Failure Scenario")
    public void testGetBookByIsbn_NotFound() throws Exception {
        when(libraryService.findBookByIsbn("978-1-56619-909-4")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/978-1-56619-909-4"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book with ISBN 978-1-56619-909-4 not found"));
    }

    @Test
    @WithMockUser
    @DisplayName("Get All Registered Books Success Scenario")
    public void testGetAllBooks_Found() throws Exception {
        List<Book> books = Arrays.asList(book1, book2);
        when(libraryService.listAllBooks()).thenReturn(Optional.of(books));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value(book1.getIsbn()))
                .andExpect(jsonPath("$[1].isbn").value(book2.getIsbn()));
    }

    @Test
    @WithMockUser
    @DisplayName("Get All Registered Books Failure Scenario")
    public void testGetAllBooks_NotFound() throws Exception {
        when(libraryService.listAllBooks()).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().string("No Books Found"));
    }

    @Test
    @WithMockUser
    @DisplayName("Get All Available Books Success Scenario")
    public void testGetAllAvailableBooks_Found() throws Exception {
        List<Book> books = Arrays.asList(book1, book2);
        when(libraryService.listAvailableBooks()).thenReturn(Optional.of(books));

        mockMvc.perform(get("/api/books/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value(book1.getIsbn()))
                .andExpect(jsonPath("$[1].isbn").value(book2.getIsbn()));
    }

    @Test
    @WithMockUser
    @DisplayName("Get All Available Books Failure Scenario")
    public void testGetAllAvailableBooks_NotFound() throws Exception {
        when(libraryService.listAvailableBooks()).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/available"))
                .andExpect(status().isOk())
                .andExpect(content().string("No Books Available"));
    }

    @Test
    @WithMockUser
    @DisplayName("Get All Books matching given Title Success Scenario")
    public void testGetBookByTitle_Found() throws Exception {
        List<Book> books = Arrays.asList(book1);
        when(libraryService.findBookByTitle("The Adventures of Sherlock Holmes")).thenReturn(Optional.of(books));

        mockMvc.perform(get("/api/books/title/The Adventures of Sherlock Holmes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value(book1.getIsbn()));
    }

    @Test
    @WithMockUser
    @DisplayName("Get All Books matching given Title Failure Scenario")
    public void testGetBookByTitle_NotFound() throws Exception {
        when(libraryService.findBookByTitle("The Adventures of Sherlock Holmes")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/title/The Adventures of Sherlock Holmes"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book with Title The Adventures of Sherlock Holmesnot found"));
    }

    @Test
    @WithMockUser
    @DisplayName("Get Books By Author Success Scenario")
    public void testGetBookByAuthor_Found() throws Exception {
        List<Book> books = Arrays.asList(book1);
        when(libraryService.findBookByAuthor("Arthur Conan Doyle")).thenReturn(Optional.of(books));

        mockMvc.perform(get("/api/books/author/Arthur Conan Doyle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value(book1.getIsbn()));
    }

    @Test
    @WithMockUser
    @DisplayName("Get Books By Author Failure Scenario")
    public void testGetBookByAuthor_NotFound() throws Exception {
        when(libraryService.findBookByAuthor("Arthur Conan Doyle")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/author/Arthur Conan Doyle"))
                .andExpect(status().isOk())
                .andExpect(content().string("No Books by Author Arthur Conan DoyleFound"));
    }

    @Test
    @WithMockUser
    @DisplayName("Remove Book from Library Success Scenario")
    public void testRemoveBook_Success() throws Exception {
        when(libraryService.removeBook("978-1-56619-909-4")).thenReturn(1L);

        mockMvc.perform(delete("/api/books/978-1-56619-909-4"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
