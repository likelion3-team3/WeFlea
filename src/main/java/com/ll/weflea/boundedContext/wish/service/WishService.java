package com.ll.weflea.boundedContext.wish.service;

import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.repository.GoodsRepository;
import com.ll.weflea.boundedContext.goods.service.GoodsService;
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
    private final GoodsRepository goodsRepository;

    public List<Wish> findWishList(Member member) {
        return wishRepository.findAllByMember_Id(member.getId());
    }

    public boolean isGoodsWished(Long memberId, Long goodsId) {

        Wish wish = wishRepository.findByMember_IdAndGoods_Id(memberId, goodsId).orElse(null);

        if (wish == null) {
            return false;
        }

        return true;
    }

    @Transactional
    public RsData<Wish> addWish(Member member, Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId).orElse(null);

        Wish wish = Wish.create(member, goods);

        return RsData.of("S-1", "해당 상품이 찜 목록에 등록되었습니다.", wish);
    }

    @Transactional
    public RsData<Wish> deleteWish(Long id) {

        wishRepository.deleteById(id);

        return RsData.of("S-2", "해당 상품이 찜 목록에서 제외되었습니다.");
    }
}
