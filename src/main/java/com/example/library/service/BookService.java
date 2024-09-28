package com.example.library.service;

import com.example.library.dao.BookDao;
import com.example.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BookService, kitap yönetimi ile ilgili işlemleri gerçekleştiren servis sınıfıdır.
 * Bu sınıf, kitap ekleme, bulma, geri verme ve listeleme işlemlerini yönetir.
 */
@Service
public class BookService {

    private final BookDao bookDao;

    /**
     * BookService constructor'ı.
     */
    @Autowired
    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    /**
     * Verilen kitap kimliğine göre kitabı bulur.
     */
    public Book findBookById(int bookId) {
        return bookDao.findBookById(bookId);
    }

    /**
     * Tüm kitapları listeleyerek döndürür.
     */
    public List<Book> listAllBooks() {
        List<Book> books = bookDao.getAllBooks();
        return books;
    }
}
