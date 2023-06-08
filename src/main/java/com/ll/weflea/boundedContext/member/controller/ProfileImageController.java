package com.ll.weflea.boundedContext.member.controller;

import com.ll.weflea.base.rq.Rq;
import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.entity.ProfileImage;
import com.ll.weflea.boundedContext.member.service.MemberService;
import com.ll.weflea.boundedContext.member.service.ProfileImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/user/member")
@RequiredArgsConstructor
@Slf4j
public class ProfileImageController {
    private final MemberService memberService;
    private final Rq rq;
    private final ProfileImageService profileImageService;

    @PostMapping("/upload/profile")
    public String uploadProfile(@AuthenticationPrincipal User user,
                                @RequestParam("profileImage") MultipartFile profileImage) throws IOException {

        String username = user.getUsername();
        RsData<ProfileImage> profileImageRsData = profileImageService.uploadProfileImage(username, profileImage);

        if (profileImageRsData.isFail()) {
            return rq.historyBack(profileImageRsData);
        }
        return rq.redirectWithMsg("/user/member/me", profileImageRsData);
    }

    @GetMapping("/profile-image")
    public ResponseEntity<byte[]> getProfileImg (@AuthenticationPrincipal User user) throws IOException {

        String username = user.getUsername();
        ResponseEntity<byte[]> profileImage = profileImageService.getProfileImg(username);

        return profileImage;
    }

}
