
package com.example.library.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(int bookId) {
        super("Kitap veritabanında bulunamadı: " + bookId);
    }
}
