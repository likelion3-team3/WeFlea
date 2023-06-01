package com.ll.weflea.boundedContext.goods.controller;

import com.ll.weflea.base.rq.Rq;
import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.repository.GoodsRepository;
import com.ll.weflea.boundedContext.goods.service.GoodsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/weflea")
public class GoodsController {
    private final Rq rq;
    private GoodsService goodsService;

    @GetMapping("/list")
    public String wefleaList() {
        return "/user/weflea/list";
    }

    // showCreate 로 변경할까 고민중
    @GetMapping("/create")
    public String wefleaCreate() {
        return "/user/weflea/weflea_form";
    }

    // 입력받은 상품 가져오기
    @AllArgsConstructor
    @Getter
    public static class CreateForm {
        private final String title;
        private final String area;
        private final String status;
        private final int price;
        private final String description;
    }


    // 위플리 상품 등록 기능 구현
    @PostMapping("/create")
    public String create(@Valid CreateForm createForm) {
        // 서비스에서 추가 기능 구현
        RsData<Goods> createGoods = goodsService.create(
                createForm.getTitle(),
                createForm.getArea(),
                createForm.getStatus(),
                createForm.getPrice(),
                createForm.getDescription()
        );


        // 게시물 등록 후 위플리 장터 목록 페이지로 다시 이동
        return "redirect:/user/weflea/list";
    }

}
