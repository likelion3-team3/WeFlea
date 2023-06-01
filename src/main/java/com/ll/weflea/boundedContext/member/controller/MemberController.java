package com.ll.weflea.boundedContext.member.controller;

import com.ll.weflea.boundedContext.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/member")
@RequiredArgsConstructor
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


}
