package com.imdev.webchat.soket;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessage {

    private Long chatRoomId;
    private String user;
    private String message;
    private MessageType type;

}
