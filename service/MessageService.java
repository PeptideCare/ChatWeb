package com.imdev.webchat.service;

import com.imdev.webchat.domain.Message;
import com.imdev.webchat.repository.MessageRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;

    // 저장
    @Transactional
    public void save(Message message) {
        messageRepository.save(message);
    }

    // 채팅방 별 모든 메세지 불러오기
    public List<Message> findAllByChatId(Long id) {
        return messageRepository.findAllByChatIt(id);
    }
}
