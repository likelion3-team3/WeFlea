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

//    public Goods() {
//        // 기본 값 설정
//        this.area = "지역";
//        this.status = Status.구매가능;
//        this.securePayment = false;
//        this.price = 1;
//        this.description = "기본 설명";
//    }
//
//    public Goods(Member member, String title, String area, Status status,
//                 boolean securePayment, int price, String description) {
//        this.member = member;
//        this.title = title;
//        this.area = area;
//        this.status = status;
//        this.securePayment = securePayment;
//        this.price = price;
//        this.description = description;
//    }


    public void setMember(Member member) {
        this.member = member;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSecurePayment(boolean securePayment) {
        this.securePayment = securePayment;
    }

    public void setDescription(String description) {
        this.description = description;
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
