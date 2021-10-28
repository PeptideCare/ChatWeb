package com.imdev.webchat.controller;

import com.imdev.webchat.domain.Chat;
import com.imdev.webchat.domain.Member;
import com.imdev.webchat.service.ChatService;
import com.imdev.webchat.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@Getter
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final MemberService memberService;

    // 방만들기
    @GetMapping("/chat/insert")
    public String saveForm(Model model) {
        model.addAttribute("chat", new ChatForm());
        return "chat/make_room";
    }

    @PostMapping("/chat/insert")
    public String save(@Valid ChatForm form, HttpSession session) {
        String id = (String)session.getAttribute("memberId");
        Member findMember = memberService.findById(id);

        Chat chat = new Chat(form.getName(), form.getMax_num(), 1, findMember.getSchool_name());
        chatService.save(chat, findMember);

        return "chat/main";
    }

    // 학교별 모든 방 조회
    @GetMapping("/chat/find/new")
    public String findAll(Model model, HttpSession session) {

        // 접속해있는 회원의 학교이름 불러오기
        String memberId = (String)session.getAttribute("memberId");
        Member findMember = memberService.findById(memberId);

        // 학교별 모든 방 조회
        List<Chat> chats = chatService.findAllBySchoolName(findMember.getSchool_name());
        model.addAttribute("chatList", chats);
        return "chat/new_chat";
    }

}
