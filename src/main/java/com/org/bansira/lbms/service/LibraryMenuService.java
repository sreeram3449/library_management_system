/**
 * This package contains service layer implementations of Library APIs.
 */
package com.org.bansira.lbms.service;

import com.org.bansira.lbms.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Interactive CLI Menu for Library Management
 */
@Service
public class LibraryMenuService implements CommandLineRunner {

    @Autowired
    private LibraryService libraryService;

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        boolean exit = false;

        while (!exit) {
            System.out.println();
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addBook();
                case 2 -> removeBook();
                case 3 -> findBookByIsbn();
                case 4 -> findBookByTitle();
                case 5 -> findBookByAuthor();
                case 6 -> listAllBooks();
                case 7 -> listAvailableBooks();
                case 8 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("Library Management System");
        System.out.println("1. Add Book");
        System.out.println("2. Remove Book");
        System.out.println("3. Find Book by ISBN");
        System.out.println("4. Find Book by Title");
        System.out.println("5. Find Book by Author");
        System.out.println("6. List All Books");
        System.out.println("7. List Available Books");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }


    private void addBook() {
        System.out.print("Enter department ID: ");
        String departmentId = scanner.nextLine();
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter publication year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Is the book available (true/false): ");
        boolean available = scanner.nextBoolean();
        scanner.nextLine(); // consume newline

        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublicationYear(year);
        book.setIsAvailable(available);
        book.setDepartment(departmentId);
        libraryService.addBook(book);
        System.out.println("Book added to department.");
    }

    private void removeBook() {
        System.out.print("Enter book ISBN to remove: ");
        String id = scanner.nextLine();
        libraryService.removeBook(id);
        System.out.println("Book removed.");
    }

    private void findBookByIsbn() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        Optional<Book> book = libraryService.findBookByIsbn(title);
        System.out.println("Book: " + book);
    }

    private void findBookByTitle() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        Optional<List<Book>> books = libraryService.findBookByTitle(title);
        books.get().forEach(System.out::println);
    }

    private void findBookByAuthor() {
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        Optional<List<Book>> books = libraryService.findBookByAuthor(author);
        books.get().forEach(System.out::println);
    }

    private void listAllBooks() {
        Optional<List<Book>> books = libraryService.listAllBooks();
        books.get().forEach(System.out::println);
    }

    private void listAvailableBooks() {
        Optional<List<Book>> books = libraryService.listAvailableBooks();
        books.get().forEach(System.out::println);
    }
}
