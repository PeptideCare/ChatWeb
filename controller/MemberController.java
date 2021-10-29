package com.imdev.webchat.controller;

import com.imdev.webchat.domain.Member;
import com.imdev.webchat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
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

    //로그인
    @GetMapping("/member/login")
    public String loginForm(Model model) {
        model.addAttribute("member", new Member());
        return "member/login";
    }

    @PostMapping("/member/login")
    public String login(@Valid Member form, BindingResult result, HttpSession session) {

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
            session.setAttribute("memberId", findMember.getId());
            return "redirect:/chat/find/mine";
        }
        // 비밀번호 일치하지 않을 시
        else {
            return "member/login";
        }
    }

    // 비밀번호 체크
    @GetMapping("/member/check")
    public String checkForm(Model model) {
        model.addAttribute("member", new Member());
        return "member/check_info";
    }

    // 비밀번호 변경 이동
    @PostMapping("/member/change")
    public String check_pw(@Valid Member member, HttpSession session) {

        String id = (String)session.getAttribute("memberId");
        Member findMember = memberService.findById(id);

        // 비밀번호 일치
        if (findMember.getPw().equals(member.getPw())) {
            // 비밀번호 변경
            return "redirect:/member/change_con";
        }
        // 불일치
        else {
            return "member/check_info";
        }
    }

    // 회원탈퇴 이동
    @PostMapping("/member/remove")
    public String check_remove(@Valid Member member, HttpSession session) {

        String id = (String)session.getAttribute("memberId");
        Member findMemer = memberService.findById(id);

        // 비밀번호 일치
        if (findMemer.getPw().equals(member.getPw())) {
            // 회원탈퇴 이동
            return "redirect:/member/remove_con";
        }
        // 불일치
        else {
            return "member/check_info";
        }
    }

    // 비밀번호 변경
    @GetMapping("/member/change_con")
    public String changeForm(Model model) {
        model.addAttribute("member", new Member());
        return "member/change_pw";
    }

    @PostMapping("/member/change_con")
    public String change(@Valid Member member, BindingResult result, HttpSession session) {
        String id = (String)session.getAttribute("memberId");

        // 변경감지
        memberService.change_pw(id, member.getPw());
        return "member/login";
    }

    // 회원탈퇴
    @GetMapping("/member/remove_con")
    public String removeForm(Model model) {
        model.addAttribute("member", new Member());
        return "member/remove_member";
    }

    @PostMapping("/member/remove_con")
    public String remove(@Valid Member member, HttpSession session) {
        String id = (String)session.getAttribute("memberId");

        Member findMember = memberService.findById(id);
        // 비밀번호 일치
        if (findMember.getPw().equals(member.getPw())) {
            memberService.remove(id);
            return "member/login";
        }
        // 불일치
        else {
            return "member/remove_member";
        }


    }

}
