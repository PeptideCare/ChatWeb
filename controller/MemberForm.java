package com.imdev.webchat.controller;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class MemberForm {

    private String id;
    private String pw;
    private String name;
    private String sex;
    private String nickname;
    private String birth;
    private String phone_number;
    private String school_name;
}
