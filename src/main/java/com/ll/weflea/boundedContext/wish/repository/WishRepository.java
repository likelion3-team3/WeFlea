package com.ll.weflea.boundedContext.wish.repository;

import com.ll.weflea.boundedContext.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {

    @Query("SELECT w FROM Wish w JOIN FETCH w.member m JOIN FETCH w.goods g WHERE m.id = :id ORDER BY w.id DESC ")
    List<Wish> findAllByMember_IdAndDOrderByIdDesc(@Param("id") Long id);

    @Query("SELECT w FROM Wish w WHERE w.member.id = :memberId and w.goods.id  = :goodsId")
    Optional<Wish> findByMember_IdAndGoods_Id(@Param("memberId") Long memberId, @Param("goodsId") Long goodsId);
}
