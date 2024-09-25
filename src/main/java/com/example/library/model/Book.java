package com.example.library.model;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    private String title;
    private String author;
    private String publisher;
    private int year;
    private boolean isBorrowed;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    public Book() {
    }
    public Book(String title, String author, String publisher, int year) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
    }
    public Book(String title, String author, String publisher, int year, Member member) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.member = member;

    }

    // Parametrelerle yapıcı metod
    public Book(String title, String author, String publisher, int year, boolean isBorrowed) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.isBorrowed = isBorrowed;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", year=" + year +
                ", isBorrowed=" + isBorrowed +
                '}';
    }
}
