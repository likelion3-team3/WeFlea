package com.ll.weflea.boundedContext.goods.entity;

import com.ll.weflea.base.entity.BaseEntity;
import com.ll.weflea.boundedContext.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

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

    @Enumerated(EnumType.STRING)
    private Status status = Status.구매가능;

    private int price;

    private String description;

    private boolean securePayment;


    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    private Member buyer;

    @OneToMany(mappedBy = "goods")
    private List<GoodsImage> goodsImages = new ArrayList<>();

    public void updateStatusAndBuyer(String status, Member buyer) {

        this.status = Status.valueOf(status);
        this.buyer = buyer;
    }

    public void updateStatus(String status) {

        this.status = Status.valueOf(status);
    }




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
