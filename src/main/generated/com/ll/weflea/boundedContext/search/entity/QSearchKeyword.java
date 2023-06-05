package com.ll.weflea.boundedContext.search.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSearchKeyword is a Querydsl query type for SearchKeyword
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSearchKeyword extends EntityPathBase<SearchKeyword> {

    private static final long serialVersionUID = -1536603267L;

    public static final QSearchKeyword searchKeyword = new QSearchKeyword("searchKeyword");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QSearchKeyword(String variable) {
        super(SearchKeyword.class, forVariable(variable));
    }

    public QSearchKeyword(Path<? extends SearchKeyword> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSearchKeyword(PathMetadata metadata) {
        super(SearchKeyword.class, metadata);
    }

}

