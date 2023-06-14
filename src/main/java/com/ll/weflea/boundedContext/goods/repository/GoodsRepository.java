package com.ll.weflea.boundedContext.goods.repository;

import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("SELECT g from Goods g inner join g.buyer b where b.id = :buyerId")
    List<Goods> findByBuyerId(@Param("buyerId") Long buyerId);

    @Query("SELECT g FROM Goods g WHERE g.title LIKE %:keyword%")
    Page<Goods> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT g FROM Goods g LEFT JOIN FETCH g.goodsImages gi")
    Page<Goods> findAll(Pageable pageable);

    Page<Goods> findByMember(Member member, Pageable pageable);

    @Query("SELECT g from Goods g left join fetch g.goodsImages where g.id = :id")
    Optional<Goods> findById(@Param("id") Long id);

}
