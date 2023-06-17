package com.ll.weflea.boundedContext.goods.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoods is a Querydsl query type for Goods
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGoods extends EntityPathBase<Goods> {

    private static final long serialVersionUID = -397176542L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGoods goods = new QGoods("goods");

    public final com.ll.weflea.base.entity.QBaseEntity _super = new com.ll.weflea.base.entity.QBaseEntity(this);

    public final StringPath area = createString("area");

    public final com.ll.weflea.boundedContext.member.entity.QMember buyer;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath description = createString("description");

    public final ListPath<GoodsImage, QGoodsImage> goodsImages = this.<GoodsImage, QGoodsImage>createList("goodsImages", GoodsImage.class, QGoodsImage.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final com.ll.weflea.boundedContext.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final BooleanPath securePayment = createBoolean("securePayment");

    public final EnumPath<Status> status = createEnum("status", Status.class);

    public final StringPath title = createString("title");

    public QGoods(String variable) {
        this(Goods.class, forVariable(variable), INITS);
    }

    public QGoods(Path<? extends Goods> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGoods(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGoods(PathMetadata metadata, PathInits inits) {
        this(Goods.class, metadata, inits);
    }

    public QGoods(Class<? extends Goods> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new com.ll.weflea.boundedContext.member.entity.QMember(forProperty("buyer"), inits.get("buyer")) : null;
        this.member = inits.isInitialized("member") ? new com.ll.weflea.boundedContext.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

