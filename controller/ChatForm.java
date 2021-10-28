package com.imdev.webchat.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ChatForm {

    @NotEmpty(message = "방 제목은 필수 입니다.")
    private String name;

    @NotNull(message = "인원수 제한은 필수 입니다.")
    private int max_num;

}
