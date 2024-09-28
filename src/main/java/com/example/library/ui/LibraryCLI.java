package com.example.library.ui;

import com.example.library.dao.BookDao;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import com.example.library.service.BorrowTransactionService;

import com.example.library.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
/**
 * LibraryCLI, kütüphane yönetim sisteminin kullanıcı arayüzünü sağlayan sınıftır.
 * Bu sınıf, kullanıcıdan aldığı girdilere göre kitap, üye ve ödünç alma işlemlerini yönetir.
 */
@Component
public class LibraryCLI implements CommandLineRunner {

    private final BookService bookService;
    private final MemberService memberService;
    private final BorrowTransactionService borrowTransactionService;
    private final BookDao bookDao;

    @Autowired
    public LibraryCLI(BookService bookService, MemberService memberService, BorrowTransactionService borrowTransactionService, BookDao bookDao) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.borrowTransactionService = borrowTransactionService;

        this.bookDao = bookDao;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Kütüphane Yönetim Sistemi");
            System.out.println("1. Kitap Yönetimi");
            System.out.println("2. Üye Yönetimi");
            System.out.println("3. Ödünç Alma ve İade İşlemleri");
            System.out.println("4. Çıkış");
            System.out.print("Seçiminizi yapın: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manageBooks(scanner);
                    break;
                case 2:
                    manageMembers(scanner);
                    break;
                case 3:
                    manageBorrowing(scanner);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Geçersiz seçim! Tekrar deneyin.");
            }
        }
    }

    private void manageBooks(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("Kitap Yönetimi");
            System.out.println("1. Kitap Ekle");
            System.out.println("2. Kitap Güncelle");
            System.out.println("3. Kitap Sil");
            System.out.println("4. Kitapları Listele");
            System.out.println("5. Geri");
            System.out.print("Seçiminizi yapın: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    updateBook(scanner);
                    break;
                case 3:
                    deleteBook(scanner);
                    break;
                case 4:
                    listBooks();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Geçersiz seçim! Tekrar deneyin.");
            }
        }
    }

    private void manageMembers(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("Üye Yönetimi");
            System.out.println("1. Üye Ekle");
            System.out.println("2. Üye Güncelle");
            System.out.println("3. Üye Sil");
            System.out.println("4. Üyeleri Listele");
            System.out.println("5. Geri");
            System.out.print("Seçiminizi yapın: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addMember(scanner);
                    break;
                case 2:
                    updateMember(scanner);
                    break;
                case 3:
                    deleteMember(scanner);
                    break;
                case 4:
                    listMembers();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Geçersiz seçim! Tekrar deneyin.");
            }
        }
    }

    private void manageBorrowing(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("Ödünç Alma ve İade İşlemleri");
            System.out.println("1. Kitap Ödünç Ver");
            System.out.println("2. Kitap İade Al");
            System.out.println("3. Geri");
            System.out.print("Seçiminizi yapın: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    borrowBook(scanner);
                    break;
                case 2:
                    returnBook(scanner);
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Geçersiz seçim! Tekrar deneyin.");
            }
        }
    }

    private void addBook(Scanner scanner) {
        scanner.nextLine();  // Buffer temizlemesi için
        System.out.print("Kitap Başlığı: ");
        String title = scanner.nextLine();
        System.out.print("Yazar: ");
        String author = scanner.nextLine();
        System.out.print("Yayıncı: ");
        String publisher = scanner.nextLine();
        System.out.print("Yıl: ");
        int year = scanner.nextInt();

        Book book = new Book(title, author, publisher, year); // ID'siz constructor kullanımı
        bookDao.addBook(book);
        System.out.println("Kitap başarıyla eklendi.");
    }

    private void updateBook(Scanner scanner) {
        System.out.print("Güncellenecek kitap ID'si: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();  // Buffer temizlemesi için

        Book book = bookService.findBookById(bookId);
        if (book != null) {
            System.out.print("Yeni Kitap Başlığı (boş bırakılırsa değişmez): ");
            String title = scanner.nextLine();
            if (!title.isEmpty()) {
                book.setTitle(title);
            }

            System.out.print("Yeni Yazar (boş bırakılırsa değişmez): ");
            String author = scanner.nextLine();
            if (!author.isEmpty()) {
                book.setAuthor(author);
            }

            System.out.print("Yeni Yayıncı (boş bırakılırsa değişmez): ");
            String publisher = scanner.nextLine();
            if (!publisher.isEmpty()) {
                book.setPublisher(publisher);
            }

            System.out.print("Yeni Yıl (boş bırakılırsa değişmez): ");
            String yearInput = scanner.nextLine();
            if (!yearInput.isEmpty()) {
                int year = Integer.parseInt(yearInput);
                book.setYear(year);
            }

            bookDao.updateBook(book);
            System.out.println("Kitap başarıyla güncellendi.");
        } else {
            System.out.println("Kitap bulunamadı!");
        }
    }

    private void deleteBook(Scanner scanner) {
        System.out.print("Silinecek kitap ID'si: ");
        int bookId = scanner.nextInt();

        try {
            bookDao.deleteBook(bookId);
            System.out.println("Kitap başarıyla silindi.");
        } catch (Exception e) {
            System.out.println("Bilinmeyen bir hata oluştu: " + e.getMessage());
        }
    }

    private void listBooks() {
        System.out.println("Mevcut Kitaplar:");
        List<Book> allBooks = bookService.listAllBooks(); // Tüm kitaplar listeleniyor

        for (Book book : allBooks) {
            String status = book.isBorrowed() ? "ÖDÜNÇTE" : "MÜSAİT";
            System.out.println(book.getBookId() + " - " + book.getTitle() + " (Yazar: " + book.getAuthor() + ") - Durum: " + status);
        }
    }

    private void addMember(Scanner scanner) {
        scanner.nextLine();  // Buffer temizlemesi için
        System.out.print("Üye Adı: ");
        String firstName = scanner.nextLine();
        System.out.print("Üye Soyadı: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefon: ");
        String phone = scanner.nextLine();

        memberService.addMember(firstName, lastName, email, phone);

        System.out.println("Üye başarıyla eklendi.");
    }

    private void updateMember(Scanner scanner) {
        System.out.print("Güncellenecek üye ID'si: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();  // Buffer temizlemesi için

        if (memberService.findMemberById(memberId) != null) {
            System.out.print("Yeni Üye Adı (boş bırakılırsa değişmez): ");
            String firstName = scanner.nextLine();
            System.out.print("Yeni Üye Soyadı (boş bırakılırsa değişmez): ");
            String lastName = scanner.nextLine();
            System.out.print("Yeni Email (lütfen doldurun**): ");
            String email = scanner.nextLine();
            System.out.print("Yeni Telefon (boş bırakılırsa değişmez): ");
            String phone = scanner.nextLine();

            memberService.updateMember(memberId, firstName, lastName, email, phone);
            System.out.println("Üye başarıyla güncellendi.");
        } else {
            System.out.println("Üye bulunamadı!");
        }
    }

    private void deleteMember(Scanner scanner) {
        System.out.print("Silinecek üye ID'si: ");
        int memberId = scanner.nextInt();

        try {
            memberService.deleteMember(memberId);
            System.out.println("Üye başarıyla silindi.");
        } catch (Exception e) {
            System.out.println("Beklenmeyen bir hata oluştu: " + e.getMessage());
        }
    }

    private void listMembers() {
        System.out.println("Mevcut Üyeler:");
        memberService.listAllMembers().forEach(member ->
                System.out.println("ID: " + member.getId() + " - İsim: " + member.getFirstName() + " " + member.getLastName())
        );
    }

    private void borrowBook(Scanner scanner) {
        System.out.print("Ödünç alınacak kitap ID'si: ");
        int bookId = scanner.nextInt();
        System.out.print("Üye ID'si: ");
        int memberId = scanner.nextInt();

        try {
            borrowTransactionService.borrowBook(memberId, bookId);
            System.out.println("Kitap başarıyla ödünç alındı.Kitabı iade etmek için üç ayınız var.");
        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    private void returnBook(Scanner scanner) {
        System.out.print("İade edilecek işlem ID'si: ");
        int transactionId = scanner.nextInt();

        try {
            borrowTransactionService.returnBook(transactionId);
            System.out.println("Kitap başarıyla iade edildi.");
        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }
}
