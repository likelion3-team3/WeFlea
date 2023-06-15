package com.ll.weflea.boundedContext.goods.controller;

import com.ll.weflea.base.rq.Rq;
import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.entity.GoodsImage;
import com.ll.weflea.boundedContext.goods.entity.Status;
import com.ll.weflea.boundedContext.goods.service.GoodsImageService;
import com.ll.weflea.boundedContext.goods.service.GoodsService;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.repository.MemberRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.domain.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/weflea")
@Slf4j
public class GoodsController {
    private final Rq rq;
    private final GoodsService goodsService;
    private final MemberRepository memberRepository;
    private final GoodsImageService goodsImageService;


    @GetMapping("/list")
    public String wefleaList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<Goods> goodsList = this.goodsService.getGoodsList(page);
        model.addAttribute("goodsList", goodsList);

        return "user/weflea/list";
    }

    @GetMapping("/list/search")
    public String searchGoods(Model model, @RequestParam("keyword") String keyword, @RequestParam(defaultValue = "0") int page) {
        Page<Goods> goodsList = goodsService.getGoodsListByKeyword(keyword, page);
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("keyword", keyword);

        return "user/weflea/list";
    }


    @GetMapping("/create")
    public String wefleaCreate(Model model) {
        // CreateForm 객체를 모델에 추가
        model.addAttribute("createForm", new CreateForm());
        return "user/weflea/form";
    }

    // 입력받은 상품 가져오기
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CreateForm {
        @NotEmpty(message = "제목을 입력해 주세요.")
        private String title;
        private String area;
        private Status status;
        private boolean securePayment;

        @NotNull(message="가격은 필수항목 입니다.")
        @Min(value = 0, message = "가격은 0보다 크거나 같아야 합니다.")
        private Integer price;

        @NotEmpty(message="내용을 입력해 주세요.")
        private String description;
        private List<MultipartFile> images;

        public CreateForm() {
            this.area = "지역";
            this.status = Status.구매가능;
            this.securePayment = false;
            this.images = null;
        }
    }

    // 위플리 상품 등록 기능 구현
    @PostMapping("/create")
    public String create(@Validated CreateForm createForm, BindingResult bindingResult, @AuthenticationPrincipal User user, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류가 있는 경우 폼 페이지로 다시 이동
            model.addAttribute(bindingResult);

            return "user/weflea/form";
        }

        // 현재 로그인한 사용자의 username 가져오기
        String username = user.getUsername();

        // username을 기반으로 member 객체 찾기
        Member member = memberRepository.findByUsername(username).orElse(null);

        // 서비스에서 추가 기능 구현
        RsData<Goods> createRsData = goodsService.create(member, createForm);

        if (createRsData.isFail()) {
            return rq.historyBack(createRsData);
        }


        // 게시물 등록 후 위플리 장터 목록 페이지로 다시 이동
        return rq.redirectWithMsg("/user/weflea/list", createRsData);
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) throws IOException {
        Goods goods = goodsService.findById(id);
        List<GoodsImage> goodsImageList = goods.getGoodsImages();

        model.addAttribute("goodsImages", goodsImageList);

        model.addAttribute("goods", goods);

        return "user/weflea/detail";
    }

    @PostMapping("/detail/delete/{id}")
    public String delete(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {

        Goods goods = goodsService.findById(id);

        if (!goods.getMember().getUsername().equals(user.getUsername())) {
            return rq.historyBack("삭제할 수 있는 권한이 없습니다.");
        }

        goodsImageService.deleteByGoods(goods);
        goodsService.deleteById(id);

        return rq.redirectWithMsg("/user/weflea/list", "게시물이 삭제되었습니다.");
    }

    @PostMapping("/detail/update/{id}")
    public String update(@PathVariable Long id, @RequestParam(required = false) String status) {

        log.info("status = {}", status);

        RsData<Goods> rsData = goodsService.updateStatus(id, status);

        return rq.redirectWithMsg("/user/weflea/detail/" + id, rsData);
    }

    @GetMapping("/goodsImage/{id}")
    public ResponseEntity<byte[]> getGoodsImg(@PathVariable("id") Long id) throws IOException {
        Goods goods = goodsService.findById(id);
        ResponseEntity<byte[]> goodsImage = goodsService.getGoodsImg(goods);

        return goodsImage;
    }

    @GetMapping("/detail/goodsImage/{id}")
    public ResponseEntity<byte[]> getGoodsImgs(@PathVariable("id") Long id) throws IOException {
        GoodsImage goodsImage = goodsImageService.findById(id);
        ResponseEntity<byte[]> goodsImg = goodsImageService.getGoodsImg(goodsImage);

        return goodsImg;
    }

    @GetMapping("/modify/{id}")
    public String wefleaModify(@PathVariable("id") Long id, Model model) {
        Goods goods = goodsService.findById(id);

        if (goods == null) {
            return "/user/weflea/detail";
        }

        model.addAttribute("goods", goods);

        return "user/weflea/modify";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id, @Valid CreateForm createForm,
                         BindingResult bindingResult, @AuthenticationPrincipal User user,
                         Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            model.addAttribute("goods", goodsService.findById(id));
            return "user/weflea/modify";
        }

        String username = user.getUsername();

        Member member = memberRepository.findByUsername(username).orElse(null);

        Goods goods = goodsService.findById(id);

        if (!goods.getMember().getUsername().equals(user.getUsername())) {
            return rq.historyBack("수정할 수 있는 권한이 없습니다.");
        }

        RsData<Goods> modifyRsData = goodsService.modify(goods, member, createForm);

        if (modifyRsData.isFail()) {
            return rq.historyBack(modifyRsData);
        }

        return "redirect:/user/weflea/detail/" + modifyRsData.getData().getId();
    }
}