package com.ll.weflea.boundedContext.member.controller;

import com.ll.weflea.boundedContext.member.dto.NicknameDto;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        NicknameDto nicknameDto = new NicknameDto();
        model.addAttribute("nicknameDto", nicknameDto);

        return "user/member/updateNickname";
    }

    @PostMapping("/update/nickname")
    public String updateNickname(@RequestParam String nickname, @AuthenticationPrincipal User user) {
        String username = user.getUsername();
        Member member = memberService.findByUsername(username).orElse(null);
        log.info("username={}", member.getUsername());

        memberService.updateNickname(member, nickname);

        return "redirect:/";
    }

}
