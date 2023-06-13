package com.ll.weflea.base.interceptor;

import com.ll.weflea.base.rq.Rq;
import com.ll.weflea.boundedContext.chat.controller.ChatController;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class NicknameCheckInterceptorTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private Rq rq;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("로그인 하지 않은 사용자는 로그인 페이지로 이동")
    public void t01() throws Exception {
        mockMvc.perform(get("/user/member/me"))
                .andExpect(redirectedUrl("/user/member/login"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("로그인 했지만 닉네임이 없으면 닉네임 설정 페이지로 이동")
    @WithUserDetails("bigsand123")
    public void t02() throws Exception {

        //when
        ResultActions resultActions = mockMvc.perform(get("/chat/rooms")).andDo(print());

        //then
        resultActions
                .andExpect(redirectedUrl("/user/member/update/nickname"))
                .andExpect(status().is3xxRedirection());
    }


    @Test
    @DisplayName("로그인 성공 시 닉네임이 있으면 요청 페이지로 이동")
    @WithUserDetails("bigsand")
    public void t03() throws Exception {

        //given
        Member member = memberService.findByUsername("bigsand").orElse(null);
        member.updateNickname("큰모래");

        //when
        ResultActions resultActions = mockMvc.perform(get("/chat/rooms")).andDo(print());

        //then
        resultActions
                .andExpect(handler().handlerType(ChatController.class))
                .andExpect(handler().methodName("myRooms"))
                .andExpect(status().is2xxSuccessful());
    }



}