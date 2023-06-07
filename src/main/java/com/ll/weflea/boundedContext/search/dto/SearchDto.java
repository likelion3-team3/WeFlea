package com.ll.weflea.boundedContext.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {

    private String keyword;

    private String provider;

    private Integer sortCode;


}