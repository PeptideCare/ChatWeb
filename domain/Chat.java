package com.imdev.webchat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Chat {

    public Chat(String name, int max_num, int now_num, String school_name) {
        this.name = name;
        this.max_num = max_num;
        this.now_num = now_num;
        this.school_name = school_name;
    }

    @Id
    @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    private String name;
    private int max_num;
    private int now_num;
    private String school_name;

    @OneToMany(mappedBy = "chat")
    private List<ChatItem> chatItem;

    //인원수 +1
    public void plus() {
        this.now_num += 1;
    }
}
