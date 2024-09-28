package com.example.library.service;

import com.example.library.dao.BookDao;
import com.example.library.dao.BorrowTransactionDao;
import com.example.library.dao.MemberDao;
import com.example.library.exception.BookAlreadyBorrowedException;
import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.MaxBooksBorrowedException;
import com.example.library.exception.MemberNotFoundException;
import com.example.library.model.Book;
import com.example.library.model.BorrowTransaction;
import com.example.library.model.Member;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * BorrowTransactionService, ödünç alma işlemleri ile ilgili iş mantığını yöneten servis sınıfıdır.
 * Bu sınıf, kitap ödünç alma, iade etme ve işlem listeleme gibi işlemleri yönetir.
 */
@Service
public class BorrowTransactionService {

    private final BorrowTransactionDao borrowTransactionDao;
    private final BookDao bookDao;
    private final MemberDao memberDao;

    /**
     * BorrowTransactionService constructor'ı.
     */
    public BorrowTransactionService(BorrowTransactionDao borrowTransactionDao, BookDao bookDao, MemberDao memberDao) {
        this.borrowTransactionDao = borrowTransactionDao;
        this.bookDao = bookDao;
        this.memberDao = memberDao;
    }


    /**
     * Üye kimliği ve kitap kimliği ile ödünç alma işlemini gerçekleştirir.
     */
    public void borrowBook(int memberId, int bookId) {
        Member member = memberDao.getMemberById(memberId);
        Book book = bookDao.getBookById(bookId);

        if (member == null) {
            throw new MemberNotFoundException(memberId);
        }

        if (book == null) {
            throw new BookNotFoundException(bookId);
        }

        if (book.isBorrowed()) {
            throw new BookAlreadyBorrowedException(bookId);
        }

        if (member.getBorrowedBooks().size() > 3) {
            throw new MaxBooksBorrowedException(memberId);
        }

        // Kitap artık ödünç alındı
        book.setBorrowed(true);
        bookDao.updateBook(book);
        member.addBorrowedBook(book);
        memberDao.updateMember(member);

        BorrowTransaction transaction = new BorrowTransaction(
                0, member, book, new Date());
        borrowTransactionDao.addTransaction(transaction);
    }

    /**
     * Verilen işlem kimliği ile ödünç alma işlemini iade eder.
     * */
    public void returnBook(int transactionId) {
        try {
            BorrowTransaction transaction = borrowTransactionDao.findTransactionById(transactionId);
            if (transaction != null && transaction.getReturnDate() == null) {
                transaction.setReturnDate(new Date());

                Book book = transaction.getBook();
                book.setBorrowed(false);
                bookDao.updateBook(book);

                borrowTransactionDao.updateTransaction(transaction);

            } else {
                if (transaction == null) {
                    System.out.println("İşlem bulunamadı.");
                } else {
                    System.out.println("Kitap zaten iade edilmiş.");
                }
            }
        } catch (Exception e) {
            System.out.println("Kitap iade edilirken hata: " + e.getMessage());
        }
    }

    /**
     * Tüm ödünç alma işlemlerini listeler.
     */
    public List<BorrowTransaction> listTransactions() {
        try {
            return borrowTransactionDao.getAllTransactions(); // Tüm işlemleri veritabanından alır
        } catch (Exception e) {
            System.out.println("Error listing transactions: " + e.getMessage());
            return List.of(); // Hata durumunda boş bir liste döner
        }
    }
}
