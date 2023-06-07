package com.ll.weflea.boundedContext.wish.service;

import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.wish.entity.Wish;
import com.ll.weflea.boundedContext.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishService {

    private final WishRepository wishRepository;

    public List<Wish> findWishList(Member member) {
        return wishRepository.findAllByMember_Id(member.getId());
    }
}
