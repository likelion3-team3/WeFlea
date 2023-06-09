package com.ll.weflea.boundedContext.member.repository;

import com.ll.weflea.boundedContext.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findByUsername(String name);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findById(Long id);
}
