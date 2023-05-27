package com.ll.weflea.base.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString
public abstract class File extends BaseEntity {
    private String name;

    //확장자 (jpg, png ....)
    private String file_type;

    private String path;

    private int type_code;

}
