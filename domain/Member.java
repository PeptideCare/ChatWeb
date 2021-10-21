package com.imdev.webchat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Member {

    public Member(String id, String pw, String nickname, String name, String sex, String birth, String phone_number, String school_name) {
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.phone_number = phone_number;
        this.school_name = school_name;
    }

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
