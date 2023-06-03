package com.ll.weflea.boundedContext.goods.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoodsImage is a Querydsl query type for GoodsImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGoodsImage extends EntityPathBase<GoodsImage> {

    private static final long serialVersionUID = 8097113L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGoodsImage goodsImage = new QGoodsImage("goodsImage");

    public final com.ll.weflea.base.entity.QFile _super = new com.ll.weflea.base.entity.QFile(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final StringPath file_type = _super.file_type;

    public final QGoods goods;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath imageLink = createString("imageLink");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath path = _super.path;

    //inherited
    public final NumberPath<Integer> type_code = _super.type_code;

    public QGoodsImage(String variable) {
        this(GoodsImage.class, forVariable(variable), INITS);
    }

    public QGoodsImage(Path<? extends GoodsImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGoodsImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGoodsImage(PathMetadata metadata, PathInits inits) {
        this(GoodsImage.class, metadata, inits);
    }

    public QGoodsImage(Class<? extends GoodsImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.goods = inits.isInitialized("goods") ? new QGoods(forProperty("goods"), inits.get("goods")) : null;
    }

}

