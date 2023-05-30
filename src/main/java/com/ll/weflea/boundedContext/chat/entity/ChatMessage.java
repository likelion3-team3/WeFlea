package com.ll.weflea.boundedContext.chat.entity;

import com.ll.weflea.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ChatMessage extends BaseEntity {

    private String contents;

    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatMember chatMember;

}
