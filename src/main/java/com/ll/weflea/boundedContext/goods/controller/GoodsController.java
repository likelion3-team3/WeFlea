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
import lombok.Setter;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/weflea")
public class GoodsController {
    private final Rq rq;
    private final GoodsService goodsService;

    @GetMapping("/list")
    public String wefleaList() {
        return "/user/weflea/list";
    }

    // showCreate 로 변경할까 고민중
    @GetMapping("/create")
    public String wefleaCreate(Model model) {
        // CreateForm 객체를 모델에 추가
         model.addAttribute("createForm", new CreateForm());


        return "user/weflea/weflea_form";
    }

    // 입력받은 상품 가져오기
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CreateForm {
        private String title;
        private String area;
        private String status;
        private int price;
        private String description;

        public CreateForm() {
            this.title = "제목";
            this.area = "지역";
            this.status = "기본 상태";
            this.price = 1;
            this.description = "기본 설명";
        }

        // Getter 메서드 추가
        public String getTitle() {
            return title;
        }

        public String getArea() {
            return area;
        }

        public String getStatus() {
            return status;
        }

        public int getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }
    }


    // 위플리 상품 등록 기능 구현
    @PostMapping("/create")
    public String create(@Valid CreateForm createForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류가 있는 경우 폼 페이지로 다시 이동
            return "user/weflea/weflea_form";
        }

        // 서비스에서 추가 기능 구현
        RsData<Goods> createRsData = goodsService.create(
                createForm.getTitle(),
                createForm.getArea(),
                createForm.getStatus(),
                createForm.getPrice(),
                createForm.getDescription()
        );

        if (createRsData.isFail()) {
            return rq.historyBack(createRsData);
        }


        // 게시물 등록 후 위플리 장터 목록 페이지로 다시 이동
        return rq.redirectWithMsg("/user/weflea/list", createRsData);
    }

}
