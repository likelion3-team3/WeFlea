package com.ll.weflea.boundedContext.search.entity;

import com.ll.weflea.base.entity.File;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class SearchImage extends File {

    private String image_link;


    @ManyToOne
    private Search search;

}
