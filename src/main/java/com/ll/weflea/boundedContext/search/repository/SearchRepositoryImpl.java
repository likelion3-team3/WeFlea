package com.ll.weflea.boundedContext.search.repository;

import com.ll.weflea.boundedContext.search.dto.SearchDto;
import com.ll.weflea.boundedContext.search.entity.Search;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ll.weflea.boundedContext.search.entity.QSearch.search;


@RequiredArgsConstructor
@Slf4j
public class SearchRepositoryImpl implements SearchRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Search> findSearchesBySellDate(SearchDto searchDto, Pageable pageable) {

        String keyword = searchDto.getKeyword();
        String provider = searchDto.getProvider();
        Integer sortCode = searchDto.getSortCode();
        LocalDateTime lastSellDate = searchDto.getSellDate();

        return jpaQueryFactory.selectFrom(search)
                .where(
                        containsKeyword(keyword),
                        eqProvider(provider),
                        ltSellDate(lastSellDate)
                )
                .orderBy(sortSearchList(sortCode))
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Search> findSearchesByPrice(SearchDto searchDto, Pageable pageable) {

        String keyword = searchDto.getKeyword();
        String provider = searchDto.getProvider();
        Integer sortCode = searchDto.getSortCode();
        Integer lastPrice = searchDto.getPrice();

        return jpaQueryFactory.selectFrom(search)
                .where(
                        containsKeyword(keyword),
                        eqProvider(provider),
                        loePrice(lastPrice)
                )
                .orderBy(sortSearchList(sortCode))
                .limit(pageable.getPageSize())
                .fetch();
    }


    private BooleanExpression containsKeyword(String keyword) {
        if (StringUtils.isNullOrEmpty(keyword)) {
            return null;
        }

        return search.title.contains(keyword);
    }

    private BooleanExpression eqProvider(String provider) {
        if (StringUtils.isNullOrEmpty(provider)) {
            return null;
        }

        return search.provider.eq(provider);
    }

    private BooleanExpression ltSellDate(LocalDateTime lastSellDate) {
        if (lastSellDate == null) {
            return null;
        }

        return search.sellDate.lt(lastSellDate);
    }

    private BooleanExpression loePrice(Integer lastPrice) {
        if (lastPrice == null) {
            return null;
        }

        return search.price.loe(lastPrice);
    }



    private OrderSpecifier[] sortSearchList(Integer sortCode) {

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
