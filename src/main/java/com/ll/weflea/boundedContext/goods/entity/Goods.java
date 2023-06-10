package com.ll.weflea.boundedContext.goods.entity;

import com.ll.weflea.base.entity.BaseEntity;
import com.ll.weflea.boundedContext.member.entity.Member;
import jakarta.persistence.*;
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


    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "goods")
    private List<GoodsImage> goodsImages = new ArrayList<>();


/*
    // 게시글 수정 메소드
    public void updateGoods(Goods goods){
        if(goods.title != null){
            this.title = goods.title;
        }
        if(goods.description != null){
            this.description = goods.description;
        }
    }
*/



}
