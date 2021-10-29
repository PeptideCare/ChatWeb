package com.imdev.webchat.soket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdev.webchat.domain.Chat;
import com.imdev.webchat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ChatRepository chatRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("메세지 전송 = {} : {}" , session, message.getPayload());
        String msg = message.getPayload();
        ChatMessage chatMessage = objectMapper.readValue(msg, ChatMessage.class);
        Chat findChat = chatRepository.findById(chatMessage.getChatRoomId());
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomId(findChat.getId());
        chatRoom.setName(findChat.getName());
        chatRoom.handleMessage(session,chatMessage,objectMapper);
    }
}
