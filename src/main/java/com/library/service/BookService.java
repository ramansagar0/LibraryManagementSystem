
package com.library.service;

import java.util.List;

import com.library.dao.BookDAO;
import com.library.model.Book;

public class BookService {

    private final BookDAO bookDAO;

    public BookService() {
        bookDAO = new BookDAO();
    }

    // Add book
    public boolean addBook(Book book) {

        if (book.getTitle() == null ||
                book.getTitle().trim().isEmpty()) {
            return false;
        }

        if (book.getAuthor() == null ||
                book.getAuthor().trim().isEmpty()) {
            return false;
        }

        if (book.getQuantity() <= 0) {
            return false;
        }

        return bookDAO.addBook(book);
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    // Search books
    public List<Book> searchBooks(String keyword) {

        if (keyword == null) {
            return List.of();
        }

        return bookDAO.searchBooks(keyword);
    }

    // Update book
    public boolean updateBook(Book book) {

        if (book.getBookId() <= 0) {
            return false;
        }

        if (book.getTitle() == null ||
                book.getTitle().trim().isEmpty()) {
            return false;
        }

        if (book.getAuthor() == null ||
                book.getAuthor().trim().isEmpty()) {
            return false;
        }

        if (book.getQuantity() < 0) {
            return false;
        }

        return bookDAO.updateBook(book);
    }

    // Delete book
    public boolean deleteBook(int bookId) {

        if (bookId <= 0) {
            return false;
        }

        return bookDAO.deleteBook(bookId);
    }
}