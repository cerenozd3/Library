package com.example.library.dao;

import com.example.library.model.BorrowTransaction;
import com.example.library.model.Book;
import com.example.library.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class BorrowTransactionDao {

    private final JdbcTemplate jdbcTemplate;

    public BorrowTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BorrowTransaction addTransaction(BorrowTransaction transaction) {
        String sql = "INSERT INTO borrowtransactions (bookId, memberId, borrowDate, returnDate) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                transaction.getBook().getBookId(),
                transaction.getMember().getId(),
                new Date(transaction.getBorrowDate().getTime()),
                transaction.getReturnDate() != null ? new Date(transaction.getReturnDate().getTime()) : null
        );
        return transaction;
    }

    public BorrowTransaction findTransactionById(int transactionId) {
        String sql = "SELECT * FROM borrowtransactions WHERE transactionId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{transactionId}, (rs, rowNum) -> {
            int bookId = rs.getInt("bookId");
            int memberId = rs.getInt("memberId");
            Date borrowDate = rs.getDate("borrowDate");
            Date returnDate = rs.getDate("returnDate");

            // Üye bilgilerini al
            Member member = jdbcTemplate.queryForObject("SELECT * FROM members WHERE memberId = ?", new Object[]{memberId}, (rs1, rowNum1) -> new Member(
                    rs1.getString("first_name"),
                    rs1.getString("last_name"),
                    rs1.getString("email"),
                    rs1.getString("phone")
            ));
            member.setId(memberId);

            // Kitap bilgilerini al
            Book book = jdbcTemplate.queryForObject("SELECT * FROM books WHERE bookId = ?", new Object[]{bookId}, (rs2, rowNum2) -> new Book(
                    rs2.getString("title"),
                    rs2.getString("author"),
                    rs2.getString("publisher"),
                    rs2.getInt("year"),
                    rs2.getBoolean("is_borrowed")
            ));
            book.setBookId(bookId);

            BorrowTransaction transaction = new BorrowTransaction(transactionId, member, book, borrowDate);
            transaction.setReturnDate(returnDate);
            return transaction;
        });
    }



    public List<BorrowTransaction> getAllTransactions() {
        String sql = "SELECT * FROM borrowtransactions";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            int transactionId = rs.getInt("transactionId");
            int bookId = rs.getInt("bookId");
            int memberId = rs.getInt("memberId");
            Date borrowDate = rs.getDate("borrowDate");
            Date returnDate = rs.getDate("returnDate");

            Member member = new Member();
            Book book = new Book();

            BorrowTransaction transaction = new BorrowTransaction(transactionId, member, book, borrowDate);
            transaction.setReturnDate(returnDate);
            return transaction;
        });
    }

    public BorrowTransaction updateTransaction(BorrowTransaction updatedTransaction) {
        String sql = "UPDATE borrowtransactions SET bookId = ?, memberId = ?, borrowDate = ?, returnDate = ? WHERE transactionId = ?";

        jdbcTemplate.update(sql,
                updatedTransaction.getBook().getBookId(),
                updatedTransaction.getMember().getId(),
                new Date(updatedTransaction.getBorrowDate().getTime()),
                updatedTransaction.getReturnDate() != null ? new Date(updatedTransaction.getReturnDate().getTime()) : null,
                updatedTransaction.getTransactionId()
        );

        if (updatedTransaction.getReturnDate() != null) {
            String updateBookSql = "UPDATE books SET is_borrowed = 0 WHERE bookId = ?";
            jdbcTemplate.update(updateBookSql, updatedTransaction.getBook().getBookId());
        }

        return updatedTransaction;
    }



    public boolean deleteTransactionById(int transactionId) {
        String sql = "DELETE FROM borrowtransactions WHERE transactionId = ?";
        int affectedRows = jdbcTemplate.update(sql, transactionId);
        return affectedRows > 0;
    }
}
