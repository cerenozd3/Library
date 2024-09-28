package com.example.library.dao;

import com.example.library.exception.MemberNotFoundException;
import com.example.library.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MemberDao, üye veritabanı işlemlerini gerçekleştiren sınıftır.
 * Bu sınıf, üye ekleme, güncelleme, silme ve sorgulama işlemlerini yönetir.
 */
@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    /**
     * MemberDao constructor'ı.
     */
    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Yeni bir üyeyi veritabanına ekler.
     */
    public void addMember(Member member) {
        String sql = "INSERT INTO members (first_name, last_name, email, phone) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, member.getFirstName(), member.getLastName(), member.getEmail(), member.getPhone());
    }

    /**
     * Verilen üye kimliğine göre üyeyi getirir.
     */
    public Member getMemberById(int id) {
        String sql = "SELECT * FROM members WHERE memberId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Member member = new Member(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone")
            );
            member.setId(rs.getInt("memberId"));
            return member;
        });
    }

    /**
     * Tüm üyeleri veritabanından getirir.
     */
    public List<Member> getAllMembers() {
        String sql = "SELECT * FROM members";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Member member = new Member(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone")
            );
            member.setId(rs.getInt("memberId"));
            return member;
        });
    }

    /**
     * Verilen üye nesnesinin bilgilerini günceller.
     */
    public void updateMember(Member member) {
        String sql = "UPDATE members SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE memberId = ?";
        jdbcTemplate.update(sql, member.getFirstName(), member.getLastName(), member.getEmail(), member.getPhone(), member.getId());
    }

    /**
     * Verilen üye kimliğine göre üyeyi siler.
     */
    public void deleteMember(int memberId) {
        String checkSql = "SELECT COUNT(*) FROM members WHERE memberId = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, new Object[]{memberId}, Integer.class);

        if (count == null || count == 0) {
            throw new MemberNotFoundException(memberId);
        }

        String updateSql = "UPDATE borrowtransactions SET memberId = NULL WHERE memberId = ?";
        jdbcTemplate.update(updateSql, memberId);

        String deleteSql = "DELETE FROM members WHERE memberId = ?";
        jdbcTemplate.update(deleteSql, memberId);
    }
}
