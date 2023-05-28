package com.ll.weflea.boundedContext.member.entity;

import com.ll.weflea.base.entity.File;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Profile extends File {

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;



}
