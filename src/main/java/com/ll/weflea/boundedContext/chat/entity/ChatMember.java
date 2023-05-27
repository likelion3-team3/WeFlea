package com.ll.weflea.boundedContext.chat.entity;

import com.ll.weflea.base.entity.BaseEntity;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ChatMember extends BaseEntity {

    @ManyToOne
    private ChatRoom chatRoom;

    //멤버와의 관계에 대한 추가 정리가 필요함.
    @ManyToOne
    private Member fromMember;

    @ManyToOne
    private Member toMember;

    @OneToMany(mappedBy = "chatMember")
    private List<ChatContent> chatContents = new ArrayList<>();

    @ManyToOne
    private Goods goods;

}
