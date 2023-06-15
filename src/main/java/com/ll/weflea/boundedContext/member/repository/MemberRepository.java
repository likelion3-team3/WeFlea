package com.ll.weflea.boundedContext.member.repository;

import com.ll.weflea.boundedContext.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{

    @Query("SELECT m from Member m left join fetch m.profileImage p where m.username = :name")
    Optional<Member> findByUsername(@Param("name") String name);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findById(Long id);
}
