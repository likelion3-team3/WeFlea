package com.ll.weflea.boundedContext.search.entity;

import com.ll.weflea.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Search extends BaseEntity {

    private String title;

    private String status;

    private int price;

    private String description;

    private String link;

    @OneToMany(mappedBy = "search")
    private List<SearchImage> searchImages = new ArrayList<>();

}
