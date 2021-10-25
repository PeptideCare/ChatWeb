package com.imdev.webchat.service;

import com.imdev.webchat.domain.Member;
import com.imdev.webchat.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public String join(Member member) {
        // 중복 회원일때
        if (!validateMember(member)) {
            return "0";
        }
        return memberRepository.save(member);
    }

    //중복 회원 방지
    public boolean validateMember(Member member) {
        Member findMember = findById(member.getId());
        //존재하는 회원일때
        if (findMember.getId() != null) {
            return false;
        }
        return true;
    }

    //조회
    public Member findById(String id) {
        return memberRepository.findById(id);
    }

    //모든 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    //탈퇴
    @Transactional
    public void remove(String id) {
        Member findMember = memberRepository.findById(id);
        memberRepository.delete(findMember);
    }

    // 비밀번호 변경
    @Transactional
    public void change_pw(String id, String pw) {
        Member findMember = memberRepository.findById(id);
        findMember.setPw(pw);
    }
}
