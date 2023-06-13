package com.ll.weflea.boundedContext.goods.repository;

import com.ll.weflea.boundedContext.goods.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("SELECT g from Goods g inner join g.buyer b where b.id = :buyerId")
    List<Goods> findByBuyerId(@Param("buyerId") Long buyerId);

    @Query("SELECT g from Goods g where g.title = :title")
    Page<Goods> findByKeyword(@Param("title") String keyword, Pageable pageable);

    Page<Goods> findAll(Pageable pageable);
}
