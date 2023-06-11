package com.ll.weflea.boundedContext.goods.service;

import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.controller.GoodsController;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.entity.GoodsImage;
import com.ll.weflea.boundedContext.goods.repository.GoodsRepository;
import com.ll.weflea.boundedContext.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


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

    //안전결제완료되면 상품에 대한 buyer 및 거래상태 설정
    @Transactional
    public void updateStatusAndBuyer(Long id, String status, Member buyer) {
        Goods goods = findById(id);
        goods.updateStatusAndBuyer(status, buyer);
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

    public ResponseEntity<List<byte[]>> getAllGoodsImages(Goods goods) throws IOException {
        List<byte[]> imageList = new ArrayList<>();

        List<GoodsImage> goodsImages = goods.getGoodsImages();

        for (GoodsImage goodsImage : goodsImages) {
            InputStream inputStream = new FileInputStream(goodsImage.getPath());
            byte[] imageByteArray = IOUtils.toByteArray(inputStream);
            inputStream.close();
            imageList.add(imageByteArray);
        }

        return new ResponseEntity<>(imageList, HttpStatus.OK);
    }

    public List<Goods> findByBuyerId(Long buyerId) {
        return goodsRepository.findByBuyerId(buyerId);
    }

}
