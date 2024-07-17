package com.org.bansira.lbms.controller;

import com.org.bansira.lbms.domain.Book;
import com.org.bansira.lbms.security.TestSecurityConfig;
import com.org.bansira.lbms.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @BeforeEach
    public void setUp() {
        book1 = new Book("The Adventures of Sherlock Holmes", "978-1-56619-909-4", "Arthur Conan Doyle", "Mystery", 1892, "Literature", true);
        book2 = new Book("1984", "978-0-45228-425-0", "George Orwell", "Dystopian", 1949, "Literature", true);
    }

    @Test
    @WithMockUser
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
    public void testGetBookByIsbn_Found() throws Exception {
        when(libraryService.findBookByIsbn("978-1-56619-909-4")).thenReturn(Optional.of(book1));

        mockMvc.perform(get("/api/books/978-1-56619-909-4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(book1.getIsbn()));
    }

    @Test
    @WithMockUser
    public void testGetBookByIsbn_NotFound() throws Exception {
        when(libraryService.findBookByIsbn("978-1-56619-909-4")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/978-1-56619-909-4"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book with ISBN 978-1-56619-909-4 not found"));
    }

    @Test
    @WithMockUser
    public void testGetAllBooks_Found() throws Exception {
        List<Book> books = Arrays.asList(book1, book2);
        when(libraryService.listAllBooks()).thenReturn(Optional.of(books));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value(book1.getIsbn()))
                .andExpect(jsonPath("$[1].isbn").value(book2.getIsbn()));
    }
}
