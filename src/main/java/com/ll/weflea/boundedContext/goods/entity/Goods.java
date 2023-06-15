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

    @OneToMany(mappedBy = "goods")
    private List<GoodsImage> goodsImages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Member buyer;


    public void updateStatusAndBuyer(String status, Member buyer) {

        this.buyer = buyer;
        this.status = Status.valueOf(status);
    }

    public void updateStatus(String status) {

        this.status = Status.valueOf(status);
    }

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

    public void setBuyer(Member buyer) {
        this.buyer = buyer;
    }





}
