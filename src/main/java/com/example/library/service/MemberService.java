package com.example.library.service;

import com.example.library.dao.MemberDao;
import com.example.library.exception.MemberNotFoundException;
import com.example.library.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MemberService, üye yönetimi ile ilgili işlemleri gerçekleştiren servis sınıfıdır.
 * Bu sınıf, üye ekleme, güncelleme, silme ve sorgulama işlemlerini yönetir.
 */
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

    /**
     * Verilen üye kimliğine göre üye bilgilerini günceller.
     */
    public void updateMember(int memberId, String firstName, String lastName, String email, String phone) {
        // Üye kaydının mevcut olup olmadığını kontrol et
        Member existingMember = memberDao.getMemberById(memberId);

        // Eğer üye yoksa, MemberNotFoundException fırlat
        if (existingMember == null) {
            throw new MemberNotFoundException(memberId);
        }

        // E-posta kontrolü: Eğer email değeri boş değilse ve güncellenmişse, geçerli bir e-posta kontrolü yap
        if (email != null && !email.isEmpty()) {
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Geçersiz e-posta formatı. Lütfen güncel bir e-posta adresi girin.");
            }
        }

        // Üye nesnesini oluştur ve güncellemeyi yap
        Member member = new Member(firstName, lastName, email, phone);
        member.setId(memberId);
        memberDao.updateMember(member); // MemberDao'dan güncelleme metodunu çağır
    }

    /**
     * Verilen e-posta adresinin geçerli bir formatta olup olmadığını kontrol eder.
     */
    private boolean isValidEmail(String email) {
        // Basit bir e-posta format kontrolü
        return email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,4}$");
    }

    /**
     * Verilen üye kimliğine göre üyeyi siler.
     */
    public void deleteMember(int memberId) {
        memberDao.deleteMember(memberId);
    }

    /**
     * Verilen üye kimliğine göre üyeyi bulur.
     */
    public Member findMemberById(int memberId) {
        return memberDao.getMemberById(memberId);
    }

    /**
     * Tüm üyeleri listeler.
     */
    public List<Member> listMembers() {
        return memberDao.getAllMembers();
    }

    /**
     * Tüm üyeleri listeler.
     */
    public List<Member> listAllMembers() {
        return listMembers();
    }
}
