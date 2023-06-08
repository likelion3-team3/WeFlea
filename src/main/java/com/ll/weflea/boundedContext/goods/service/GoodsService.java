package com.ll.weflea.boundedContext.goods.service;

import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.controller.GoodsController;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.entity.GoodsImage;
import com.ll.weflea.boundedContext.goods.repository.GoodsRepository;
import com.ll.weflea.boundedContext.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.DataFormatException;


@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final GoodsImageService goodsImageService;

    // 위플리 장터 상품 등록 기능
    @Transactional
    public RsData<Goods> create(Member member, @Valid GoodsController.CreateForm createForm) throws Exception {
        try {
            Goods goods = Goods
                    .builder()
                    .member(member)
                    .title(createForm.getTitle())
                    .area(createForm.getArea())
                    .status(createForm.getStatus())
                    .price(createForm.getPrice())
                    .description(createForm.getDescription())
                    .build();

            goodsRepository.save(goods);

            MultipartFile photo = createForm.getPhoto();

            if (photo != null && !photo.isEmpty()) {
                String originalFilename = StringUtils.cleanPath(photo.getOriginalFilename());
                String extension = FilenameUtils.getExtension(originalFilename);
                String fileName = UUID.randomUUID().toString() + "." + extension;


            }

            return RsData.of("S-1", "입력하신 상품이 등록되었습니다.", goods);
        } catch (Exception e) {
            e.printStackTrace();
            return RsData.of("F-1", "상품 등록 중 오류가 발생했습니다.");
        }
    }

    public List<Goods> getGoodsList() {
        return goodsRepository.findAll();
    }

    public Goods getGoods(long id) {
        Optional<Goods> goods = goodsRepository.findById(id);

        // 존재하지 않는 상품의 id가 입력되는 경우 에러 처리
        if (goods.isPresent() == false) throw new NoSuchElementException("상품이 존재하지 않습니다.");

        return goods.get();
    }
}
