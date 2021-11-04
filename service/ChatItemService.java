package com.imdev.webchat.service;

import com.imdev.webchat.domain.Chat;
import com.imdev.webchat.domain.ChatItem;
import com.imdev.webchat.domain.Member;
import com.imdev.webchat.repository.ChatItemRepository;
import com.imdev.webchat.repository.ChatRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Getter
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatItemService {

    private final ChatItemRepository chatItemRepository;
    private final ChatRepository chatRepository;

    // 방 입장
    @Transactional
    public void save(Member member, Long chat_id) {
        Chat findChat = chatRepository.findById(chat_id);

        boolean check = chatItemRepository.findByUserIdAndChatId(member.getId(), chat_id);

        // 이미 존재
        if (!check) return;

        // 인원수 초과
        if (findChat.getMax_num() <= findChat.getNow_num()) return;

        //인원수 +1
        findChat.plus();

        ChatItem chatItem = new ChatItem(member, findChat);
        chatItemRepository.save(chatItem);
    }

    // 회원별 모든 방 조회
    public List<ChatItem> findAllById(String id) {
        return chatItemRepository.findAllById(id);
    }

}
