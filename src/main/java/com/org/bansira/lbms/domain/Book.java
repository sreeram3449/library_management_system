package com.org.bansira.lbms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Data
@Document(collection = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private String Id;
    private String title;
    private String isbn;
    private String author;
    private String genre;
    private Integer publicationYear;
    private String department;
    private Boolean isAvailable;

    public Book(String title, String isbn, String author, String genre, Integer publicationYear, String department, Boolean isAvailable) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.department = department;
        this.isAvailable = isAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
