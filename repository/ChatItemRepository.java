package com.imdev.webchat.repository;

import com.imdev.webchat.domain.Chat;
import com.imdev.webchat.domain.ChatItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Getter
public class ChatItemRepository {

    private final EntityManager em;

    //저장
    public Long save(ChatItem item) {
        em.persist(item);
        return item.getId();
    }

    //조회
    public ChatItem findById(Long id) {
        return em.find(ChatItem.class, id);
    }

    //모든 조회
    public List<ChatItem> findAll() {
        return em.createQuery("select ci from ChatItem ci")
                .getResultList();
    }

    // 회원 아이디별 모든 조회
    public List<ChatItem> findAllById(String id) {
        return em.createQuery("select ci from ChatItem ci join fetch ci.chat where ci.member.id = :id")
                .setParameter("id", id)
                .getResultList();
    }

    //삭제
    public void delete(Chat chat) {
        em.remove(chat);
    }
}
