package com.ll.weflea.boundedContext.wish.repository;

import com.ll.weflea.boundedContext.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {

    @Query("SELECT w FROM Wish w JOIN FETCH w.member m JOIN FETCH w.goods g WHERE m.id = :id")
    List<Wish> findAllByMember_Id(@Param("id") Long id);
}
