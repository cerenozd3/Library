package com.example.library.exception;

public class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException(int bookId) {
        super("Kitap zaten ödünç alınmış: " + bookId);
    }
}
