package com.ll.weflea.boundedContext.goods.repository;

import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.entity.GoodsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GoodsImageRepository extends JpaRepository<GoodsImage, Long> {
    Optional<GoodsImage> findTopByGoodsId(@Param("goodsId") Long goodsId);

    void deleteByGoods(Goods goods);
}
