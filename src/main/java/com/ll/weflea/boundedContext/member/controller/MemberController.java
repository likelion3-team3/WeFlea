package com.ll.weflea.boundedContext.member.controller;

import com.ll.weflea.base.rq.Rq;
import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.service.GoodsService;
import com.ll.weflea.boundedContext.member.dto.NicknameDto;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final GoodsService goodsService;
    private final Rq rq;

    @GetMapping("login")
    public String showLogin() {
        return "user/member/login";
    }

    @GetMapping("/me")
    public String myPage() {
        return "user/member/me";
    }

    @GetMapping("/update/nickname")
    public String showUpdateNickname(Model model) {
        model.addAttribute("nicknameDto", new NicknameDto());

        return "user/member/updateNickname";
    }

    @PostMapping("/update/nickname")
    public String updateNickname(@Valid NicknameDto nicknameDto, BindingResult bindingResult, @AuthenticationPrincipal User user) {

        if (bindingResult.hasErrors()) {
            return "user/member/updateNickname";
        }

        String username = user.getUsername();
        Member member = memberService.findByUsername(username).orElse(null);

        RsData<Member> memberRsData = memberService.updateNickname(member, nicknameDto.getNickname());

        if (memberRsData.isFail()) {
            return rq.historyBack(memberRsData);
        }
        return rq.redirectWithMsg("/", memberRsData);
    }

    @GetMapping("/me/status")
    public String showPurchaseStatus(@AuthenticationPrincipal User user, Model model) {
        Member member = memberService.findByUsername(user.getUsername()).orElse(null);
        List<Goods> goodsList = goodsService.findByBuyerId(member.getId());

        model.addAttribute("goodsList", goodsList);

        return "user/weflea/purchaseStatus";
    }
}
