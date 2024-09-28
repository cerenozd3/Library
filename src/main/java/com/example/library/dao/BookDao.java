package com.example.library.dao;

import com.example.library.exception.BookNotFoundException;
import com.example.library.model.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BookDao, kitap veritabanı işlemlerini gerçekleştiren sınıftır.
 * Bu sınıf, kitap ekleme, güncelleme, silme ve sorgulama işlemlerini yönetir.
 */
@Repository
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    /**
     * BookDao constructor'ı.
     * */
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Yeni bir kitabı veritabanına ekler.
     */
    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, publisher, year, is_borrowed) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublisher(), book.getYear(), book.isBorrowed());
    }

    /**
     * Verilen kitap kimliğine göre kitabı getirir.
     */
    public Book getBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE bookId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{bookId}, (rs, rowNum) -> {
            Book book = new Book(
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("publisher"),
                    rs.getInt("year")
            );
            book.setBookId(rs.getInt("bookId"));
            book.setBorrowed(rs.getBoolean("is_borrowed"));
            return book;
        });
    }

    /**
     * Verilen kitap kimliğine göre kitabı bulur.
     * Eğer kitap yoksa, null döndürür.
     */
    public Book findBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE bookId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{bookId}, (rs, rowNum) -> {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getInt("year")
                );
                book.setBookId(rs.getInt("bookId"));
                book.setBorrowed(rs.getBoolean("is_borrowed"));
                return book;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Verilen kitap nesnesinin bilgilerini günceller.
     */
    public void updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publisher = ?, year = ?, is_borrowed = ? WHERE bookId = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublisher(), book.getYear(), book.isBorrowed(), book.getBookId());
    }

    /**
     * Verilen kitap kimliğine göre kitabı siler.
     */
    public void deleteBook(int bookId) {
        String checkSql = "SELECT COUNT(*) FROM books WHERE bookId = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, new Object[]{bookId}, Integer.class);

        if (count == null || count == 0) {
            throw new BookNotFoundException(bookId);
        }
        String deleteTransactionsSql = "DELETE FROM borrowtransactions WHERE bookId = ?";
        jdbcTemplate.update(deleteTransactionsSql, bookId);

        String deleteBookSql = "DELETE FROM books WHERE bookId = ?";
        jdbcTemplate.update(deleteBookSql, bookId);
    }

    /**
     * Tüm kitapları veritabanından getirir.
     */
    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Book book = new Book(
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("publisher"),
                    rs.getInt("year")
            );
            book.setBookId(rs.getInt("bookId")); // Kolon adı book_id olarak güncellendi
            book.setBorrowed(rs.getBoolean("is_borrowed"));
            return book;
        });
    }
}
