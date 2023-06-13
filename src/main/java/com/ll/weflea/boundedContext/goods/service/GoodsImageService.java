package com.ll.weflea.boundedContext.goods.service;

import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.entity.GoodsImage;
import com.ll.weflea.boundedContext.goods.repository.GoodsImageRepository;
import com.ll.weflea.boundedContext.goods.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsImageService {
    private final GoodsRepository goodsRepository;
    private final GoodsImageRepository goodsImageRepository;

    @Value("${spring.servlet.multipart.location}")
    private String storageLocation;

    public RsData<List<GoodsImage>> uploadGoodsImages(Long id, List<MultipartFile> images) throws IOException {

        Goods goods = goodsRepository.findById(id).orElse(null);
        List<GoodsImage> goodsImages = new ArrayList<>();

        for(MultipartFile image : images) {

            String fileName = generatedUniqueFileName(image.getOriginalFilename());
            String filePath = storageLocation + fileName;

            log.info("goodsImage 확인 = {}", fileName);

            image.transferTo(new File(filePath));

            GoodsImage goodsImage = GoodsImage
                    .builder()
                    .goods(goods)
                    .name(fileName)
                    .path(filePath)
                    .build();

            goodsImages.add(goodsImage);

        }

        goodsImageRepository.saveAll(goodsImages);

        return RsData.of("S-1", "상품 사진이 등록되었습니다.", goodsImages);
    }

    private String generatedUniqueFileName(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        String uniqueFileName = UUID.randomUUID().toString() + "." + extension;

        return uniqueFileName;
    }

    public void deleteGoodsImage(Long id) {
        // 이미지 ID로 상품 이미지를 조회
        GoodsImage goodsImage = goodsImageRepository.findById(id).orElse(null);

        if (goodsImage == null) {
            throw new IllegalArgumentException("해당 상품의 이미지를 찾을 수 없습니다.");
        }

        String filePath = goodsImage.getPath();
        File imageFile = new File(filePath);
        if (imageFile.exists()) {
            imageFile.delete();
        }

        goodsImageRepository.delete(goodsImage);
    }

}