package com.ll.weflea.boundedContext.search.repository;

import com.ll.weflea.boundedContext.search.dto.SearchDto;
import com.ll.weflea.boundedContext.search.entity.Search;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ll.weflea.boundedContext.search.entity.QSearch.search;
import static org.springframework.util.StringUtils.hasText;


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
        LocalDateTime lastSellDate = searchDto.getSellDate();

        JPAQuery<Search> query = jpaQueryFactory.selectFrom(search)
                .where(
                        containsKeyword(keyword),
                        eqProvider(provider)
                )
                .orderBy(sortSearchList(sortCode))
                .limit(pageable.getPageSize());


        if (comparePriceAndSellDate(lastPrice, sortCode, lastSellDate) != null) {
            query.where(comparePriceAndSellDate(lastPrice, sortCode, lastSellDate));
        }

        return query.fetch();

    }

    private BooleanExpression ltSellDate(LocalDateTime lastSellDate) {
        if (lastSellDate == null) {
            return null;
        }

        return search.sellDate.lt(lastSellDate);
    }

    private BooleanExpression containsKeyword(String keyword) {
        return hasText(keyword) ? search.title.contains(keyword) : null;
    }

    private BooleanExpression eqProvider(String provider) {

        return hasText(provider) ? search.provider.eq(provider) : null;
    }


    private BooleanExpression ltPrice(Integer lastPrice) {
        if (lastPrice == null) {
            return null;
        }

        return search.price.lt(lastPrice);
    }

    private BooleanExpression gtPrice(Integer lastPrice) {
        if (lastPrice == null) {
            return null;
        }

        return search.price.gt(lastPrice);
    }

    private BooleanExpression ltPriceAndGtPrice(Integer lastPrice, Integer sortCode) {
        if (sortCode == 2) {
            return ltPrice(lastPrice);
        }

        return gtPrice(lastPrice);
    }

    private BooleanExpression comparePriceAndSellDate(Integer lastPrice, Integer sortCode, LocalDateTime lastSellDate) {

        if (ltPriceAndGtPrice(lastPrice, sortCode) == null) {
            return null;
        }


        return ltPriceAndGtPrice(lastPrice, sortCode).or(eqPriceAndltSellDate(lastPrice,lastSellDate));
    }

    private BooleanExpression eqPriceAndltSellDate(Integer lastPrice, LocalDateTime lastSellDate) {
        if (lastPrice == null && lastSellDate == null) {
            return null;
        }


        if (lastPrice == null) {
            return search.sellDate.lt(lastSellDate);
        }

        if (lastSellDate == null) {
            return search.price.eq(lastPrice);
        }

        return search.price.eq(lastPrice).and(search.sellDate.lt(lastSellDate));
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
                orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, search.sellDate));
                break;
            //가격 낮은 순
            case 3:
                orderSpecifiers.add(new OrderSpecifier<>(Order.ASC, search.price));
                orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, search.sellDate));
                break;
        }

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

}
