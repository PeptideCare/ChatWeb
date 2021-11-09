package com.imdev.webchat.service;

import com.imdev.webchat.domain.Member;
import com.imdev.webchat.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public String join(Member member) {
        // 중복 회원일때(ID 일치)
        if (validateMember(member).equals("0")) {
            return "0";
        }
        // 닉네임 일치
        else if (validateMember(member).equals("1")) {
            return "1";
        }
        // 정상적인 회원가입
        return memberRepository.save(member);
    }

    //중복 회원 방지
    public String validateMember(Member member) {
        Optional<Member> byId = findById(member.getId());


        //존재하는 회원일때(ID 체크)
        if (!byId.isEmpty()) {
            return "0";
        }
        // 닉네임 체크
        if (!memberRepository.findByNickname(member.getNickname())) {
            return "1";
        }
        // 정상적인 회원가입
        return "2";
    }

    //조회
    public Optional<Member> findById(String id) {
        return memberRepository.findById(id);
    }

    //모든 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    //탈퇴
    @Transactional
    public void remove(String id) {
        Member findMember = memberRepository.findById(id).get();
        memberRepository.delete(findMember);
    }

    //닉네임 체크
    public boolean check(String memberNickname) {
        return memberRepository.findByNickname(memberNickname);
    }

    // 비밀번호 변경
    @Transactional
    public void change_pw(String id, String pw) {
        Member findMember = memberRepository.findById(id).get();
        findMember.setPw(pw);
    }
}
