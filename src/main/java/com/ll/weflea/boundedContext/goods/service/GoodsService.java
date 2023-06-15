package com.ll.weflea.boundedContext.goods.service;

import com.ll.weflea.base.rsData.RsData;
import com.ll.weflea.boundedContext.goods.controller.GoodsController;
import com.ll.weflea.boundedContext.goods.entity.Goods;
import com.ll.weflea.boundedContext.goods.entity.GoodsImage;
import com.ll.weflea.boundedContext.goods.repository.GoodsImageRepository;
import com.ll.weflea.boundedContext.goods.repository.GoodsRepository;
import com.ll.weflea.boundedContext.member.entity.Member;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.ll.weflea.boundedContext.search.entity.QSearch.search;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final GoodsImageService goodsImageService;
    private final GoodsImageRepository goodsImageRepository;

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
                    .securePayment(createForm.isSecurePayment())
                    .price(createForm.getPrice())
                    .description(createForm.getDescription())
                    .build();

            goodsRepository.save(goods);

            List<MultipartFile> images = createForm.getImages();

            for (MultipartFile image : images) {
                if (image.isEmpty()) {
                    return RsData.of("S-1", "입력하신 상품이 등록되었습니다.", goods);
                }
            }

            goodsImageService.uploadGoodsImages(goods.getId(), images);

            return RsData.of("S-1", "입력하신 상품이 등록되었습니다.", goods);
        } catch (Exception e) {
            e.printStackTrace();
            return RsData.of("F-1", "상품 등록 중 오류가 발생했습니다.");
        }
    }

    public Page<Goods> getGoodsList(int page, Integer sortCode) {
        List<Sort.Order> sorts = new ArrayList<>();
        if (sortCode.equals(1)) {
            sorts.add(Sort.Order.desc("modifyDate"));
        }
        else if(sortCode.equals(2)){
            sorts.add(Sort.Order.desc("price"));
        }
        else{
            sorts.add(Sort.Order.asc("price"));
        }
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        return this.goodsRepository.findAll(pageable);
    }

    public Goods findById(long id) {
        Optional<Goods> goods = goodsRepository.findById(id);

        // 존재하지 않는 상품의 id가 입력되는 경우 에러 처리
        if (goods.isPresent() == false) throw new NoSuchElementException("상품이 존재하지 않습니다.");

        return goods.get();
    }

    public Page<Goods> getMyGoodsList(Member member, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
        Page<Goods> myGoodsList = goodsRepository.findByMember(member, pageable);

        return myGoodsList;
    }

    @Transactional
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

    public ResponseEntity<byte[]> getGoodsImg(Long goodsId) throws IOException {
        GoodsImage goodsImage = goodsImageRepository.findTopByGoodsId(goodsId).orElse(null);

        InputStream inputStream = new FileInputStream(goodsImage.getPath());
        byte[] imageByteArray = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }

    public List<Goods> findByBuyerId(Long buyerId) {
        return goodsRepository.findByBuyerId(buyerId);
    }

    public Page<Goods> getGoodsListByKeyword(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return goodsRepository.findByKeyword(keyword, pageable);
    }

    @Transactional
    public RsData<Goods> modify(Goods goods, Member member,
                                @Valid GoodsController.CreateForm createForm) {
        try {
            goods.setMember(member);
            goods.setTitle(createForm.getTitle());
            goods.setArea(createForm.getArea());
            goods.setStatus(createForm.getStatus());
            goods.setSecurePayment(createForm.isSecurePayment());
            goods.setPrice(createForm.getPrice());
            goods.setDescription(createForm.getDescription());

            goodsRepository.save(goods);

            List<MultipartFile> images = createForm.getImages();

            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    goodsImageRepository.deleteByGoods(goods);
                    goodsImageRepository.flush();
                    goodsImageService.uploadGoodsImages(goods.getId(), images);
                    break;
                }
            }

            return RsData.of("S-1", "상품이 수정되었습니다.", goods);
        } catch (Exception e) {
            e.printStackTrace();
            return RsData.of("F-1", "상품 수정 중 오류가 발생했습니다.");
        }
    }

    private OrderSpecifier[] sortGoodsList(Integer sortCode) {

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();


        switch (sortCode != null ? sortCode : 1) {
            //최신순
            case 1:
                orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, search.sellDate));
                break;

            //가격 높은 순
            case 2:
                orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, search.price));
                break;
            //가격 낮은 순
            case 3:
                orderSpecifiers.add(new OrderSpecifier<>(Order.ASC, search.price));
                break;
        }

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

}
