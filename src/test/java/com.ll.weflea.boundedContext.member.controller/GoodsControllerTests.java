package com.ll.weflea.boundedContext.member.controller;

import com.ll.weflea.boundedContext.goods.controller.GoodsController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class GoodsControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("위플리 상품 등록 폼")
    void t01() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/user/weflea/create")
                        .with(csrf())
                        .param("title", "title1")
                        .param("area", "area1")
                        .param("status", "status1")
                        .param("price", "2000")
                        .param("description", "desc11")
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(GoodsController.class))
                .andExpect(handler().methodName("create"))
                .andExpect(status().is3xxRedirection());

    }
}
