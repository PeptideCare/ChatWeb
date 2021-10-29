package com.imdev.webchat.soket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ChatRoom {

    private Long roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public void handleMessage(WebSocketSession session, ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        if (chatMessage.getType()==MessageType.ENTER) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getUser() +"님이 입장하셨습니다.");
        } else if (chatMessage.getType()==MessageType.LEAVE) {
            sessions.remove(session);
            chatMessage.setMessage(chatMessage.getUser() + "님이 퇴장하셨습니다.");
        } else {
            chatMessage.setMessage(chatMessage.getUser() + " : " + chatMessage.getMessage());
        }
        send(chatMessage, objectMapper);
    }
    private void send(ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(chatMessage.getMessage()));
        for (WebSocketSession session : sessions) {
            session.sendMessage(textMessage);
        }
    }

}
