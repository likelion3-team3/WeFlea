package com.ll.weflea.boundedContext.notification.entity;

import com.ll.weflea.base.entity.BaseEntity;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Notification extends BaseEntity {

    private LocalDateTime readDate;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Goods goods;
}
