package com.ll.weflea.boundedContext.goods.entity;

import com.ll.weflea.base.entity.BaseEntity;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.notification.entity.Notification;
import com.ll.weflea.boundedContext.wish.entity.Wish;
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
public class Goods extends BaseEntity {

    private String area;

    private String title;

    private String status;

    private int price;

    private String description;

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "goods")
    private List<GoodsImage> goodsImages = new ArrayList<>();


    @OneToMany(mappedBy = "goods")
    private List<Wish> wishes = new ArrayList<>();

    @OneToMany(mappedBy = "goods")
    private List<Notification> notifications = new ArrayList<>();



}
