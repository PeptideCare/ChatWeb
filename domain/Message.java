package com.imdev.webchat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Message {

    public Message(String message, LocalDateTime time, Member member, Chat chat) {
        Message = message;
        this.time = time;
        this.member = member;
        this.chat = chat;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String Message;
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

}
