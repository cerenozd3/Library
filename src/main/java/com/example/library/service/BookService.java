package com.example.library.service;

import com.example.library.dao.BookDao;
import com.example.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookDao bookDao;

    @Autowired
    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }
    public Book findBookById(int bookId) {
        return bookDao.findBookById(bookId);
    }
    public void addBook(String title, String author, String publisher, int year) {
        Book book = new Book(title, author, publisher, year);
        bookDao.addBook(book);
        System.out.println("Book added successfully.");
    }



    public void returnBook(int bookId) {
        Book book = bookDao.getBookById(bookId);
        if (book != null && book.isBorrowed()) {
            book.setBorrowed(false);
            bookDao.updateBook(book);
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Unable to return the book.");
        }
    }

    public List<Book> listAllBooks() {
        List<Book> books = bookDao.getAllBooks();
        return books;
    }
}
