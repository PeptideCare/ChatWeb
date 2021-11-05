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

    //회원아이디, 채팅아이디로 채팅방 여부 확인
    public boolean findByUserIdAndChatId(String memberId, Long chatId) {
        boolean check = em.createQuery("select ci from ChatItem ci where ci.member.id = :memberId and ci.chat.id = :chatId", ChatItem.class)
                .setParameter("memberId", memberId)
                .setParameter("chatId", chatId)
                .getResultList().isEmpty();
        return check;
    }

    //회원아이디, 검색으로 불러오기
    public List<ChatItem> findAllBySearch(String memberId, String search) {
        return em.createQuery("select ci from ChatItem ci where ci.member.id = :memberId and ci.chat.name like :search")
                .setParameter("memberId", memberId)
                .setParameter("search", "%"+search+"%")
                .getResultList();
    }

    //삭제
    public void delete(Chat chat) {
        em.remove(chat);
    }
}
