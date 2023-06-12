package com.ll.weflea.boundedContext.goods.service;

import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.controller.GoodsController;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.entity.Status;
import com.ll.weflea.boundedContext.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GoodsServiceTests {

    @Autowired
    private GoodsService goodsService;

    @Test
    void t001() throws Exception {
        Member member = new Member();
        GoodsController.CreateForm createForm = new GoodsController.CreateForm();

        createForm.setTitle("테스트");
        createForm.setArea("지역없음");
        createForm.setStatus(Status.구매가능);
        createForm.setSecurePayment(false);
        createForm.setPrice(1000);
        createForm.setDescription("상세설명");

        for (int i = 1; i <= 120; i++) {
            RsData<Goods> createRsData = goodsService.create(member, createForm);

            if (createRsData.isFail()) {
                throw new Exception("상품 등록에 실패하였습니다");
            }
        }
    }
}
