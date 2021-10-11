package com.imdev.webchat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Chat {

    @Id
    @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    private String name;
    private int maxNum;
    private String schoolName;

    @OneToMany(mappedBy = "chat")
    private List<ChatItem> chatItem;
}
