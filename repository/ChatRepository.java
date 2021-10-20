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

}
