package com.ll.weflea.boundedContext.wish.controller;

import com.ll.weflea.base.rq.Rq;
import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.service.MemberService;
import com.ll.weflea.boundedContext.wish.entity.Wish;
import com.ll.weflea.boundedContext.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/wish")
public class WishController {

    private final Rq rq;
    private final MemberService memberService;
    private final WishService wishService;

    @GetMapping("/list")
    public String showWishList(@AuthenticationPrincipal User user, Model model) {
        Member member = memberService.findByUsername(user.getUsername()).orElse(null);
        List<Wish> wishList = wishService.findWishList(member);

        model.addAttribute("wishList", wishList);

        return "wish/list";
    }

    //찜하기 -> 위플리 상품 상세 들어가서 찜하기 눌러야 함.
    @PostMapping("/add/{goodsId}")
    public String addWish(@PathVariable Long goodsId, @AuthenticationPrincipal User user) {

        if (user.getAuthorities() == null) {
            return rq.historyBack("로그인을 먼저 해주세요!");
        }

        Member member = memberService.findByUsername(user.getUsername()).orElse(null);

        if (member.getId().equals(goodsId)) {
            return rq.historyBack("본인 상품을 찜 목록에 등록할 수 없습니다.");
        }

        if (wishService.isGoodsWished(member.getId(), goodsId)) {
            return rq.historyBack("이미 찜한 상품입니다.");
        }

        RsData<Wish> rsData = wishService.addWish(member, goodsId);

        if (rsData.isFail()) {
            return rq.historyBack(rsData.getMsg());
        }

        return rq.redirectWithMsg("/user/weflea/detail/" + goodsId, rsData);
    }

    @PostMapping("/delete/{wishId}")
    public String deleteWish(@PathVariable Long id) {

        RsData<Wish> rsData = wishService.deleteWish(id);

        return rq.redirectWithMsg("user/wish/list", rsData);
    }
}
