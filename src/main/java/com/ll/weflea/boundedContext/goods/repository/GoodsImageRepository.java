package com.ll.weflea.boundedContext.goods.repository;

import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.entity.GoodsImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodsImageRepository extends JpaRepository<GoodsImage, Long> {
    Optional<GoodsImage> findByGoods(Goods goods);
}
