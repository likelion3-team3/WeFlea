package com.ll.weflea.boundedContext.goods.service;

import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.entity.GoodsImage;
import com.ll.weflea.boundedContext.goods.repository.GoodsImageRepository;
import com.ll.weflea.boundedContext.goods.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    public ResponseEntity<byte[]> getGoodsImg(GoodsImage goodsImage) throws IOException {
        InputStream inputStream = new FileInputStream(goodsImage.getPath());
        byte[] imageByteArray = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }

    public GoodsImage findById(long id) {
        Optional<GoodsImage> goodsImageOptional = goodsImageRepository.findById(id);
        if(goodsImageOptional.isEmpty()) throw new NoSuchElementException("상품 이미지가 존재하지 않습니다.");

        return goodsImageOptional.get();
    }

    private String generatedUniqueFileName(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        String uniqueFileName = UUID.randomUUID() + "." + extension;

        return uniqueFileName;
    }

    @Transactional
    public void deleteByGoods(Goods goods){
        goodsImageRepository.deleteByGoods(goods);
    }

}