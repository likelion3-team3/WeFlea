package com.ll.weflea.boundedContext.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {

    private String keyword = "";

    private String provider = "";

    private Integer sortCode = 1;

    private LocalDateTime sellDate = null;

    private Integer price = null;

}
