package com.example.library.exception;

public class MaxBooksBorrowedException extends RuntimeException {
    public MaxBooksBorrowedException(int memberId) {
        super("Üye maksimum kitap sayısına ulaştı: " + memberId);
    }
}
