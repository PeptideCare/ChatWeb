package com.imdev.webchat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Member {

    @Id
    @Column(name = "member_id")
    private String id;
    private String pw;
    private String nickname;
    private String name;
    private String sex;
    private String birth;
    private String phone_number;
    private String school_name;

    @OneToMany(mappedBy = "member")
    private List<ChatItem> chatItem;

    // 닉네임 변경
    private void change_nickname(String nickname) {
        this.nickname = nickname;
    }

    // 패스워드 변경
    private void change_pw(String pw) {
        this.pw = pw;
    }
}
