package com.ll.weflea.boundedContext.wish.controller;

import com.ll.weflea.base.rq.Rq;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.service.MemberService;
import com.ll.weflea.boundedContext.wish.entity.Wish;
import com.ll.weflea.boundedContext.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
//    @PostMapping("/add")
//    public ResponseEntity<String> addWish(long id, String likes, @AuthenticationPrincipal User user) {
//
//        if (user.getAuthorities() == null) {
//            rq.historyBack("로그인을 먼저 해주세요!");
//        }
//
//
//    }
}
