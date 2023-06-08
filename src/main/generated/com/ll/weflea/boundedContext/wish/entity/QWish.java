package com.ll.weflea.boundedContext.wish.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWish is a Querydsl query type for Wish
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWish extends EntityPathBase<Wish> {

    private static final long serialVersionUID = -1876905718L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWish wish = new QWish("wish");

    public final com.ll.weflea.base.entity.QBaseEntity _super = new com.ll.weflea.base.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final com.ll.weflea.boundedContext.goods.entity.QGoods goods;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final com.ll.weflea.boundedContext.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public QWish(String variable) {
        this(Wish.class, forVariable(variable), INITS);
    }

    public QWish(Path<? extends Wish> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWish(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWish(PathMetadata metadata, PathInits inits) {
        this(Wish.class, metadata, inits);
    }

    public QWish(Class<? extends Wish> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.goods = inits.isInitialized("goods") ? new com.ll.weflea.boundedContext.goods.entity.QGoods(forProperty("goods"), inits.get("goods")) : null;
        this.member = inits.isInitialized("member") ? new com.ll.weflea.boundedContext.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

