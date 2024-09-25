package com.example.library.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(int memberId) {
        super("Üye veritabanında bulunamadı: " + memberId);
    }
}
