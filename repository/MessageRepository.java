package com.imdev.webchat.repository;

import com.imdev.webchat.domain.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Getter
public class MessageRepository {

    private final EntityManager em;

    // 저장
    public void save(Message message) {
        em.persist(message);
    }

    // 채팅방 별 모든 메세지 불러오기
    public List<Message> findAllByChatIt(Long chatId) {
        return em.createQuery("select ms from Message ms where ms.chat.id = :chatId")
                .setParameter("chatId", chatId)
                .getResultList();
    }

}
