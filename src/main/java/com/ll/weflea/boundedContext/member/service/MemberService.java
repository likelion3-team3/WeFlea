package com.ll.weflea.boundedContext.member.service;

import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.entity.ProfileImage;
import com.ll.weflea.boundedContext.member.repository.MemberRepository;
import com.ll.weflea.boundedContext.member.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProfileImageRepository profileImageRepository;

    public Optional<Member> findByUsername(String name) {
        return memberRepository.findByUsername(name);
    }

    public Optional<Member> findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public RsData<Member> join(String providerTypeCode, String username){
        if (findByUsername(username).isPresent()) {
            return RsData.of("F-1", "해당 아이디(%s)는 이미 사용중입니다.".formatted(username));
        }

        Member member = Member
                .builder()
                .username(username)
                .providerTypeCode(providerTypeCode)
                .profileImage(null)
                .build();

        memberRepository.save(member);

        return RsData.of("S-1", "회원가입이 완료되었습니다.", member);
    }

    @Transactional
    public RsData<Member> whenSocialLogin(String providerTypeCode, String username) {
        Optional<Member> opMember = findByUsername(username);

        if (opMember.isPresent()) return RsData.of("S-2", "로그인 되었습니다.", opMember.get());

        // 소셜 로그인를 통한 가입시 비번은 없다.
        return join(providerTypeCode, username); // 최초 로그인 시 딱 한번 실행
    }

    @Transactional
    public RsData<Member> updateNickname(Member member, String nickname) {

        if (isExistNickname(nickname)) {
            return RsData.of("F-3", "이미 존재하는 닉네임입니다.");
        }

        member.updateNickname(nickname);
        return RsData.of("S-3", "닉네임이 수정되었습니다.");
    }

    private boolean isExistNickname(String nickname) {
        Member member = memberRepository.findByNickname(nickname).orElse(null);

        if (member == null) {
            return false;
        }

        return true;
    }



}
