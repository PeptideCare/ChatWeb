package com.imdev.webchat.controller;

import com.imdev.webchat.domain.Member;
import com.imdev.webchat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @GetMapping("/member/new")
    public String joinForm(Model model) {
        model.addAttribute("member", new MemberForm());
        return "member/join";
    }

    @PostMapping("/member/new")
    public String join(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "member/join";
        }

        Member member = new Member(form.getId(), form.getPw(),
                form.getName(), form.getSex(), form.getBirth(), form.getPhone_number(), form.getSchool_name());
        String id = memberService.join(member);

        //중복회원
        if (id.equals("0")) {
            System.out.println("중복");
            return "redirect:/member/new";
        } else {
            System.out.println("가입성공");
        }
        return "redirect:/member/login";
    }

    @GetMapping("/member/login")
    public String loginForm(Model model) {
        model.addAttribute("member", new Member());
        return "member/login";
    }

    @PostMapping("/member/login")
    public String login(@Valid Member form, BindingResult result) {

        if (result.hasErrors()) {
            return "member/login";
        }

        String id = form.getId();
        String pw = form.getPw();

        Member findMember = memberService.findById(id);

        // 회원이 아닐 시
        if (findMember == null) {
            return "member/login";
        }
        // 로그인 성공
        else if (pw.equals(findMember.getPw())) {
            return "chat/main";
        }
        // 비밀번호 일치하지 않을 시
        else {
            return "member/login";
        }
    }
}
