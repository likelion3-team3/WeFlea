package com.ll.weflea.boundedContext.goods.controller;

import com.ll.weflea.base.rq.Rq;
import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.entity.GoodsImage;
import com.ll.weflea.boundedContext.goods.service.GoodsImageService;
import com.ll.weflea.boundedContext.goods.service.GoodsService;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/weflea")
public class GoodsController {
    private final Rq rq;
    private final GoodsService goodsService;
    private final MemberRepository memberRepository;
    private final GoodsImageService goodsImageService;


    @GetMapping("/list")
    public String wefleaList(Model model) {
        List<Goods> goodsList = goodsService.getGoodsList();
        model.addAttribute("goodsList", goodsList);

        return "/user/weflea/list";
    }


    @GetMapping("/create")
    public String wefleaCreate(Model model) {
        // CreateForm 객체를 모델에 추가
        model.addAttribute("createForm", new CreateForm());
        return "/user/weflea/form";
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
        private MultipartFile photo;

        public CreateForm() {
            this.title = "제목";
            this.area = "지역";
            this.status = "기본 상태";
            this.price = 1;
            this.description = "기본 설명";
        }
    }

    // 위플리 상품 등록 기능 구현
    @PostMapping("/create")
    public String create(@Valid CreateForm createForm, BindingResult bindingResult, @AuthenticationPrincipal User user) throws Exception {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류가 있는 경우 폼 페이지로 다시 이동
            return "/user/weflea/form";
        }

        // 현재 로그인한 사용자의 username 가져오기
        String username = user.getUsername();

        // username을 기반으로 member 객체 찾기
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        Member member = optionalMember.get();

        // 서비스에서 추가 기능 구현
        RsData<Goods> createRsData = goodsService.create(member, createForm);

        if (createRsData.isFail()) {
            return rq.historyBack(createRsData);
        }


        // 게시물 등록 후 위플리 장터 목록 페이지로 다시 이동
        return rq.redirectWithMsg("/user/weflea/list", createRsData);
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Goods goods = goodsService.findById(id);

        model.addAttribute("goods", goods);

        return "/user/weflea/detail";
    }
}