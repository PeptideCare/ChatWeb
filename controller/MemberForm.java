package com.imdev.webchat.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 ID는 필수 입니다.")
    private String id;
    @NotEmpty(message = "비밀번호는 필수 입니다.")
    private String pw;
    @NotEmpty(message = "이름은 필수 입니다.")
    private String name;
    @NotEmpty(message = "성별은 필수 입니다.")
    private String sex;
    @NotEmpty(message = "닉네임은 필수 입니다.")
    private String nickname;
    private String birth;
    @NotEmpty(message = "핸드폰번호는 필수 입니다.")
    private String phone_number;
    @NotEmpty(message = "학교명은 필수 입니다.")
    private String school_name;
}
