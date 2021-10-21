package com.imdev.webchat.controller;

import com.imdev.webchat.domain.Member;
import com.imdev.webchat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @GetMapping("/")
    public String joinForm(Model model) {
        model.addAttribute("member", new Member());
        return "join";
    }

    @PostMapping("/join")
    public String join(Member member, BindingResult result) {
        System.out.println(member.getId());
        if (result.hasErrors()) {
            return "join";
        }

        Member member1 = new Member(member.getId(), member.getPw(), member.getNickname(),
                member.getName(), member.getSex(), member.getBirth(), member.getPhone_number(), member.getSchool_name());
        String id = memberService.join(member1);

        //중복회원
        if (id.equals("0")) {
            return "join";
        } else {
            return "login";
        }
    }
}
