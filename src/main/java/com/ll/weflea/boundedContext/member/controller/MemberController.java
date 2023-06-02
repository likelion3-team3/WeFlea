package com.ll.weflea.boundedContext.member.controller;

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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("login")
    public String showLogin(){
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
        log.info("nicknameDto = {}",nicknameDto.getNickname());

        memberService.updateNickname(member, nicknameDto.getNickname());

        return "redirect:/";
    }

}
