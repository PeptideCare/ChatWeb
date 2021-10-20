package com.imdev.webchat.service;

import com.imdev.webchat.domain.Member;
import com.imdev.webchat.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
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
        if (findMember != null) {
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
}
