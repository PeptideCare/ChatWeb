package com.imdev.webchat.repository;

import com.imdev.webchat.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Getter
public class MemberRepository {

    private final EntityManager em;

    //저장
    public String save(Member member) {
        em.persist(member);
        return member.getId();
    }

    //삭제
    public void delete(Member member) {
        em.remove(member);
    }

    //하나의 멤버 조회
    public Optional<Member> findById(String id) {

        // 널체크
        Member findMember = em.find(Member.class, id);

        Optional<Member> member = Optional.ofNullable(findMember);
        return member;
    }

    // 닉네임으로 체크
    public boolean findByNickname(String nickname) {

        return em.createQuery("select m from Member m where m.nickname = :nickname", Member.class)
                .setParameter("nickname", nickname)
                .getResultList().isEmpty();
    }

    //전체 멤버 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
