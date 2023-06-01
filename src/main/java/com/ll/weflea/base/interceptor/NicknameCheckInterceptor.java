package com.ll.weflea.base.interceptor;

import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class NicknameCheckInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null || authentication.getPrincipal() instanceof User) {
            User user = (User)authentication.getPrincipal();
            String username = user.getUsername();
            Member member = memberService.findByUsername(username).orElse(null);

            if (member == null) {
                response.sendRedirect("/user/member/login");
                return false;
            }

            if (member.getNickname() == null) {
                response.sendRedirect("/user/member/update/nickname");
                return false;
            }

            return true;
        }

        return false;
    }

}
