package com.ll.weflea.boundedContext.keyword.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKeyword is a Querydsl query type for Keyword
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKeyword extends EntityPathBase<Keyword> {

    private static final long serialVersionUID = -2081531390L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKeyword keyword = new QKeyword("keyword");

    public final com.ll.weflea.base.entity.QBaseEntity _super = new com.ll.weflea.base.entity.QBaseEntity(this);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final com.ll.weflea.boundedContext.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public QKeyword(String variable) {
        this(Keyword.class, forVariable(variable), INITS);
    }

    public QKeyword(Path<? extends Keyword> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKeyword(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKeyword(PathMetadata metadata, PathInits inits) {
        this(Keyword.class, metadata, inits);
    }

    public QKeyword(Class<? extends Keyword> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ll.weflea.boundedContext.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

