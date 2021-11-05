package com.imdev.webchat.repository;

import com.imdev.webchat.domain.Chat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Getter
public class ChatRepository {

    private final EntityManager em;

    //저장
    public Long save(Chat chat) {
        em.persist(chat);
        return chat.getId();
    }

    //삭제
    public void delete(Chat chat) {
        em.remove(chat);
    }

    //조회
    public Chat findById(Long id) {
        return em.find(Chat.class, id);
    }

    //모든 조회
    public List<Chat> findAll() {
        return em.createQuery("select c from Chat c", Chat.class)
                .getResultList();
    }

    //학교별 모든 방 조회
    public List<Chat> findAllBySchoolName(String schoolName) {
        return em.createQuery("select c from Chat c where c.school_name = :schoolName", Chat.class)
                .setParameter("schoolName", schoolName)
                .getResultList();
    }

    //검색으로 모든 방 조회
    public List<Chat> findAllBySearch(String Search, String schoolName) {
        return em.createQuery("select c from Chat c where c.name like :Search and c.school_name = :schoolName")
                .setParameter("Search", "%"+Search+"%")
                .setParameter("schoolName", schoolName)
                .getResultList();
    }

}
