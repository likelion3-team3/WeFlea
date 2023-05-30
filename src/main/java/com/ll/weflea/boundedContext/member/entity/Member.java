package com.ll.weflea.boundedContext.member.entity;

import com.ll.weflea.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity {

    private String role;

    private String name;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    private String providerTypeCode;

    public static Member create(String role, String name, String nickname, String email, String providerTypeCode) {
        return Member.builder()
                .role(role)
                .name(name)
                .nickname(nickname)
                .email(email)
                .providerTypeCode(providerTypeCode)
                .build();
    }
}
