package com.ll.weflea.boundedContext.search.repository;

import com.ll.weflea.boundedContext.search.entity.Search;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.ll.weflea.boundedContext.search.entity.QSearch.search;


@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Search> findSearchesById(Long lastSearchId, String keyword, Pageable pageable) {

        return jpaQueryFactory.selectFrom(search)
                .where(
                        ltSearchId(lastSearchId),
                        containsKeyword(keyword)
                )
                .orderBy(search.id.asc())
                .limit(pageable.getPageSize())
                .fetch();
    }


    private BooleanExpression ltSearchId(Long lastSearchId) {
        if (lastSearchId == null) {
            return null;
        }

        return search.id.gt(lastSearchId);
    }

    private BooleanExpression containsKeyword(String keyword) {
        if (StringUtils.isNullOrEmpty(keyword)) {
            return null;
        }

        return search.title.contains(keyword);
    }

}
