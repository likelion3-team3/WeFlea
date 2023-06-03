package com.ll.weflea.boundedContext.search.repository;

import com.ll.weflea.boundedContext.search.entity.Search;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.ll.weflea.boundedContext.search.entity.QSearch.search;


@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Search> findSearchesById(Long lastSearchId, Pageable pageable) {

        return jpaQueryFactory.selectFrom(search)
                .where(
                        ltSearchId(lastSearchId)
                )
                .orderBy(search.id.desc())
                .limit(pageable.getPageSize())
                .fetch();
    }


    private BooleanExpression ltSearchId(Long lastSearchId) {
        if (lastSearchId == null) {
            return null;
        }

        return search.id.lt(lastSearchId);
    }

}
