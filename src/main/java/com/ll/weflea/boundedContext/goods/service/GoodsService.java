package com.ll.weflea.boundedContext.goods.service;

import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.controller.GoodsController;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.entity.GoodsImage;
import com.ll.weflea.boundedContext.goods.repository.GoodsRepository;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.ll.weflea.boundedContext.member.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

            List<MultipartFile> images = createForm.getImages();

            if (images != null || !images.isEmpty()) {

                goodsImageService.uploadGoodsImages(goods.getId(), images);
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

    public Goods findById(long id) {
        Optional<Goods> goods = goodsRepository.findById(id);

        // 존재하지 않는 상품의 id가 입력되는 경우 에러 처리
        if (goods.isPresent() == false) throw new NoSuchElementException("상품이 존재하지 않습니다.");

        return goods.get();
    }

    public void deleteById(Long id) {
        goodsRepository.deleteById(id);
    }

    @Transactional
    public RsData<Goods> updateStatus(Long id, String status) {
        Goods goods = findById(id);
        goods.updateStatus(status);

        return RsData.of("S-1", "거래상태가 " + status + "으로 변경되었습니다.");
    }
    public ResponseEntity<byte[]> getGoodsImg(Goods goods) throws IOException {
        GoodsImage goodsImage = goods.getGoodsImages().get(0);

        InputStream inputStream = new FileInputStream(goodsImage.getPath());
        byte[] imageByteArray = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }
}
