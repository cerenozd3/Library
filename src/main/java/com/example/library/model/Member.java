package com.example.library.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Member, kütüphane sistemindeki üyeleri temsil eden bir sınıftır.
 * Bu sınıf, üye bilgilerini ve ödünç alınan kitapları içerir.
 */
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> borrowedBooks = new ArrayList<>();

    public Member() {
    }

    public Member(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }


    // Getter ve Setter metodları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public List<Book> getBorrowedBooks() { return borrowedBooks; }
    public void setBorrowedBooks(List<Book> borrowedBooks) { this.borrowedBooks = borrowedBooks; }

    public void addBorrowedBook(Book book) {
        this.borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        this.borrowedBooks.remove(book);
    }
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
