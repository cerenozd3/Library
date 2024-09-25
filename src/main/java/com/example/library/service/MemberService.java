package com.example.library.service;

import com.example.library.dao.MemberDao;
import com.example.library.exception.MemberNotFoundException;
import com.example.library.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberDao memberDao;

    @Autowired
    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void addMember(String firstName, String lastName, String email, String phone) {
        Member member = new Member(firstName, lastName, email, phone);
        memberDao.addMember(member);
    }

    public void updateMember(int memberId, String firstName, String lastName, String email, String phone) {
        Member member = new Member(firstName, lastName, email, phone);
        member.setId(memberId);
        if (member == null) {
            throw new MemberNotFoundException(memberId);

        }
        else{
        memberDao.updateMember(member);
    }}


    public void deleteMember(int memberId) {
        memberDao.deleteMember(memberId);

    }

    public Member findMemberById(int memberId) {
        return memberDao.getMemberById(memberId);
    }

    public List<Member> listMembers() {
        return memberDao.getAllMembers();
    }

    public List<Member> listAllMembers() {
        return listMembers();
    }
}
