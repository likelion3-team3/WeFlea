package com.ll.weflea.boundedContext.member;

import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    @Transactional
    public void create(String role, String name, String nickname, String email, String providerTypeCode) {
        Member member = Member.create(role, name, nickname, email, providerTypeCode);
        memberRepository.save(member);
    }
}
