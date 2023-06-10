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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


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

                // 이미지를 업로드하는 곳으로 변경
                RsData<GoodsImage> uploadRsData = goodsImageService.uploadGoodsImage(goods.getId(), photo);
                if (uploadRsData.isSuccess()) {
                    GoodsImage uploadedImage = uploadRsData.getData();
                    goods.setFilePath(uploadedImage.getPath());
                } else {
                    // 업로드 실패 시 예외 처리
                    throw new Exception("상품을 등록할 수 없습니다.");
                }
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
}
