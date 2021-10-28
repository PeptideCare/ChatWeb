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
@RequiredArgsConstructor
@Getter
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatItemRepository chatItemRepository;

    // 방 생성
    @Transactional
    public Long save(Chat chat, Member member) {
        Long id = chatRepository.save(chat);

        // 생성과 함께 입장
        ChatItem chatItem = new ChatItem(member, chat);
        chatItemRepository.save(chatItem);

        return id;
    }

    // 모든 방 조회
    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    // 학교별 모든 방 조회
    public List<Chat> findAllBySchoolName(String schoolName) {
        return chatRepository.findAllBySchoolName(schoolName);
    }

    // 방 조회
    public Chat findById(Long id) {
        Chat findChat = chatRepository.findById(id);
        return findChat;
    }
}
