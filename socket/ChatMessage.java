package com.imdev.webchat.socket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private Long chatRoomId;
    private String writer;
    private String message;
    private MessageType type;
}
