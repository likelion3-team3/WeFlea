package com.ll.weflea.boundedContext.goods.entity;

import com.ll.weflea.base.entity.File;
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
public class GoodsImage extends File {
    private String imageLink;

    @ManyToOne(fetch = FetchType.LAZY)
    private Goods goods;

}
