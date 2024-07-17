/**
 * This package contains all the DB queries for the Book collection.
 */
package com.org.bansira.lbms.data;

import com.org.bansira.lbms.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    boolean existsByIsbn(String isbn);

    Optional<Book> findByIsbn(String isbn);

    Optional<List<Book>> findByIsAvailable(Boolean isAvailable);

    Optional<List<Book>> findByTitle(String title);

    Optional<List<Book>> findByAuthor(String author);

    Long deleteByIsbn(String isbn);
}
