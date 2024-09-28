package com.example.library.model;

import java.util.Date;
/**
 * BorrowTransaction, bir ödünç alma işlemini temsil eden sınıftır.
 * Bu sınıf, hangi üyenin hangi kitabı ne zaman ödünç aldığını ve geri iade tarihini içerir.
 */
public class BorrowTransaction {
    private int transactionId;
    private Member member;
    private Book book;
    private Date borrowDate;
    private Date returnDate;


    public BorrowTransaction(int transactionId, Member member, Book book, Date borrowDate) {
        this.transactionId = transactionId;
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = null; // Başlangıçta returnDate null
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BorrowTransaction{" +
                "transactionId=" + transactionId +
                ", member=" + member +
                ", book=" + book +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
