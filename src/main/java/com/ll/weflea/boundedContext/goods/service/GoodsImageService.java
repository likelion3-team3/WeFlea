package com.ll.weflea.boundedContext.goods.service;

import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.entity.GoodsImage;
import com.ll.weflea.boundedContext.goods.repository.GoodsImageRepository;
import com.ll.weflea.boundedContext.goods.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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

    public RsData<GoodsImage> uploadGoodsImage(long id, MultipartFile image) {

        Goods goods = goodsRepository.findById(id).orElse(null);

        String fileName = generatedUniqueFileName(image.getOriginalFilename());
        Path filePath = Path.of(storageLocation, fileName);

        GoodsImage goodsImage = GoodsImage
                .builder()
                .goods(goods)
                .name(fileName)
                .path(filePath.toString())
                .build();

        try {
            // 이미지 파일 저장
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            goodsImageRepository.save(goodsImage);

            return RsData.of("S-1", "상품 사진이 등록되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            return RsData.of("F-3", "상품 사진 업로드 중 오류가 발생했습니다.");
        }
    }

    private String generatedUniqueFileName(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        String uniqueFileName = UUID.randomUUID().toString() + "." + extension;

        return uniqueFileName;
    }
}