package com.example.library.dao;

import com.example.library.exception.MemberNotFoundException;
import com.example.library.model.Book;
import com.example.library.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addMember(Member member) {
        String sql = "INSERT INTO members (first_name, last_name, email, phone) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, member.getFirstName(), member.getLastName(), member.getEmail(), member.getPhone());
    }

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

    public void updateMember(Member member) {
        String sql = "UPDATE members SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE memberId = ?";
        jdbcTemplate.update(sql, member.getFirstName(), member.getLastName(), member.getEmail(), member.getPhone(), member.getId());
    }


    public void deleteMember(int member_id) {
        String checkSql = "SELECT COUNT(*) FROM members WHERE memberId = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, new Object[]{member_id}, Integer.class);

        if (count == null || count == 0) {
            throw new MemberNotFoundException(member_id);
        }
        String sql = "DELETE FROM members WHERE memberId = ?";
        jdbcTemplate.update(sql, member_id);
    }
}
