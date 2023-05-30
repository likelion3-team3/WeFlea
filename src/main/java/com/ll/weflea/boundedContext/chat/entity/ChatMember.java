//package com.ll.weflea.boundedContext.chat.entity;
//
//import com.ll.weflea.base.entity.BaseEntity;
//import com.ll.weflea.boundedContext.goods.entity.Goods;
//import com.ll.weflea.boundedContext.member.entity.Member;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import lombok.experimental.SuperBuilder;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@NoArgsConstructor
//@SuperBuilder
//@ToString(callSuper = true)
//public class ChatMember extends BaseEntity {
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private ChatRoom chatRoom;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member fromMember;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member toMember;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Goods goods;
//
//}
