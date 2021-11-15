package com.imdev.webchat.controller;

import com.imdev.webchat.domain.Member;
import com.imdev.webchat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @GetMapping("/member/new")
    public String joinForm(Model model, HttpSession session) {
        model.addAttribute("message", session.getAttribute("message"));
        session.invalidate();
        model.addAttribute("member", new MemberForm());
        return "member/join";
    }

    @PostMapping("/member/new")
    public String join(@Valid MemberForm form, BindingResult result, HttpSession session) {

        if (result.hasErrors()) {
            return "member/join";
        }

        // 유효성 체크
        if (form.getId().isEmpty() || form.getPw().isEmpty() || form.getBirth().isEmpty() || form.getName().isEmpty()
                || form.getNickname().isEmpty() || form.getPhone_number().isEmpty() || form.getSex().isEmpty()
                || form.getSchool_name().isEmpty()) {
            session.setAttribute("message", "모든 값은 필수값입니다.");
            return "redirect:/member/new";
        }

        if (form.getId().length()<=3 || form.getPw().length()<=3 || form.getBirth().length()<=3
                || form.getNickname().length()<=3 || form.getPhone_number().length()<=3
                || form.getSchool_name().length()<=3) {
            session.setAttribute("message", "성별과 이름을 제외한 모든 값은 4글자 이상을 적어야 합니다.");
            return "redirect:/member/new";
        }

        Member member = new Member(form.getId(), form.getPw(),
                form.getName(), form.getNickname(), form.getSex(), form.getBirth(), form.getPhone_number(), form.getSchool_name());
        String id = memberService.join(member);

        //중복회원
        if (id.equals("0")) {
            session.setAttribute("message", "이미 존재하는 ID 입니다.");
            return "redirect:/member/new";
        } else if (id.equals("1")) {
            session.setAttribute("message", "이미 존재하는 닉네임 입니다.");
            return "redirect:/member/new";
        }
        session.setAttribute("message", "회원가입에 성공하셨습니다.");
        return "redirect:/member/login";
    }

    //로그인
    @GetMapping("/member/login")
    public String loginForm(Model model, HttpSession session) {
        model.addAttribute("message", session.getAttribute("message"));
        session.invalidate();
        model.addAttribute("member", new Member());
        return "member/login";
    }

    @PostMapping("/member/login")
    public String login(@Valid Member form, BindingResult result, HttpSession session, Model model) {

        if (result.hasErrors()) {
            return "member/login";
        }

        String id = form.getId();
        String pw = form.getPw();

        if (id.equals("admin") && pw.equals("admin")) {
            return "redirect:/report";
        }

        Optional<Member> findMember = memberService.findById(id);

        // 회원이 아닐 시
        if (findMember.isEmpty()) {
            model.addAttribute("message", "존재하지 않는 회원입니다.");
            return "member/login";
        }
        // 로그인 성공
        else if (pw.equals(findMember.get().getPw())) {
            session.setAttribute("memberId", findMember.get().getId());
            return "redirect:/chat/find/mine";
        }
        // 비밀번호 일치하지 않을 시
        else {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
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
    public String check_pw(@Valid Member member, HttpSession session, Model model) {

        String id = (String)session.getAttribute("memberId");
        Member findMember = memberService.findById(id).get();

        // 비밀번호 일치
        if (findMember.getPw().equals(member.getPw())) {
            // 비밀번호 변경
            return "redirect:/member/change_con";
        }
        // 불일치
        else {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            return "member/check_info";
        }
    }

    // 회원탈퇴 이동
    @PostMapping("/member/remove")
    public String check_remove(@Valid Member member, HttpSession session, Model model) {

        String id = (String)session.getAttribute("memberId");
        Member findMember = memberService.findById(id).get();

        // 비밀번호 일치
        if (findMember.getPw().equals(member.getPw())) {
            // 회원탈퇴 이동
            return "redirect:/member/remove_con";
        }
        // 불일치
        else {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
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
    public String change(@Valid Member member, BindingResult result, HttpSession session, Model model) {
        String id = (String)session.getAttribute("memberId");

        // 변경감지
        memberService.change_pw(id, member.getPw());

        model.addAttribute("message", "비밀번호가 변경되었습니다.");
        return "member/login";
    }

    // 회원탈퇴
    @GetMapping("/member/remove_con")
    public String removeForm(Model model) {
        model.addAttribute("member", new Member());
        return "member/remove_member";
    }

    @PostMapping("/member/remove_con")
    public String remove(@Valid Member member, HttpSession session, Model model) {
        String id = (String)session.getAttribute("memberId");

        Member findMember = memberService.findById(id).get();
        // 비밀번호 일치
        if (findMember.getPw().equals(member.getPw())) {
            memberService.remove(id);
            model.addAttribute("message", "회원탈퇴가 완료되었습니다.");
            return "member/login";
        }
        // 불일치
        else {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            return "member/remove_member";
        }
    }

    // ID 중복확인
    @PostMapping("/member/confirm/id")
    @ResponseBody
    public String confirmId(String memberId) {

        if (memberId.length()<=3) return "false";

        // 가능한 ID
        if (memberService.findById(memberId).isEmpty()) {
            return "true";
        }
        // 불가능
        else {
            return "false";
        }
    }

    // 닉네임 중복확인
    @PostMapping("/member/confirm/nickname")
    @ResponseBody
    public String confirmNickname(String memberNickname) {

        if (memberNickname.length() <=3) return "false";

        // 가능한 닉네임
        if (memberService.check(memberNickname)) {
            return "true";
        }
        // 불가능
        else {
            return "false";
        }
    }

}
