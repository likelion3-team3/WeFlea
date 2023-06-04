package com.ll.weflea.boundedContext.member.controller;

import com.ll.weflea.base.rq.Rq;
import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.member.dto.NicknameDto;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.entity.ProfileImage;
import com.ll.weflea.boundedContext.member.service.MemberService;
import com.ll.weflea.boundedContext.member.service.ProfileImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final Rq rq;
    private final ProfileImageService profileImageService;

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

        return rq.redirectWithMsg("/user/member/me", memberRsData);
    }

    @PostMapping("/upload/profile")
    public String uploadProfile(@AuthenticationPrincipal User user,
                                @RequestParam("profileImage") MultipartFile profileImage) {

        String username = user.getUsername();
        RsData<ProfileImage> profileImageRsData = profileImageService.uploadProfileImage(username, profileImage);

        if (profileImageRsData.isFail()) {
            return rq.historyBack(profileImageRsData);
        }
        return rq.redirectWithMsg("/user/member/me", profileImageRsData);
    }
}
