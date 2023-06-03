package com.ll.weflea.boundedContext.search.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDto {

    private Long id;

    private String title;

    private String price;

    private String link;

    private String imageLink;

    private String provider;

    private String area;



}
