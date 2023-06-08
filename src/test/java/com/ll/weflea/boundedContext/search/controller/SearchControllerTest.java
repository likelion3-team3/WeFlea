package com.ll.weflea.boundedContext.search.controller;

import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void setup() {
        //given
        Member member = memberService.findByUsername("bigsand").orElse(null);
        member.updateNickname("큰모래");
    }


    @Test
    @DisplayName("통합 검색 목록 조회 (키워드 X)")
    @WithUserDetails("bigsand")
    public void t01() throws Exception {


        //when
        ResultActions resultActions = mockMvc.perform(get("/user/search/all")
                        .with(csrf())
                )
                .andDo(print());


        //then
        resultActions
                .andExpect(handler().handlerType(SearchController.class))
                .andExpect(handler().methodName("searchAll"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/search/list"))
                .andExpect(model().attributeDoesNotExist("keyword"))
                .andExpect(model().attributeExists("keywords"))
                .andExpect(model().attributeExists("searchList"));

    }

    @Test
    @DisplayName("통합 검색 목록 조회 (키워드 O)")
    @WithUserDetails("bigsand")
    public void t02() throws Exception {


        //when
        ResultActions resultActions = mockMvc.perform(get("/user/search/all")
                        .param("keyword", "모니터")
                )
                .andDo(print());


        //then
        resultActions
                .andExpect(handler().handlerType(SearchController.class))
                .andExpect(handler().methodName("searchAll"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/search/list"))
                .andExpect(model().attributeExists("keywords"))
                .andExpect(model().attributeExists("keyword"))
                .andExpect(model().attributeExists("searchList"));

    }

//    @Test
//    @DisplayName("통합 검색 목록 조회 무한 스크롤")
//    @WithUserDetails("bigsand")
//    public void t03() throws Exception {
//
//        //given
//
//        Long lastSearchId = 10L;
//        String keyword = "노트북";
//
//
//        //when
//        ResultActions resultActions = mockMvc.perform(get("/user/search/all/{lastSearchId}", lastSearchId)
//                        .param("keyword", keyword)
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print());
//
//
//        //then
//        resultActions
//                .andExpect(handler().handlerType(SearchController.class))
//                .andExpect(handler().methodName("searchByLastSearchId"))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(jsonPath("$.keywords").isArray())
//                .andExpect(jsonPath("$.keywords[0].name").value("자전거"))
//                .andExpect(jsonPath("$.keywords[1].name").value("의자"))
//                .andExpect(jsonPath("$.searchList").isArray())
//                .andExpect(jsonPath("$.searchList[0].id").value(11L));
//
//    }






}