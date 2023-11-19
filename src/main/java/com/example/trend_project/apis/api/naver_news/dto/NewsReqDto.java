package com.example.trend_project.apis.api.naver_news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class NewsReqDto {
    private String query;
    @Builder.Default
    private int display = 3;
    @Builder.Default
    private String sort = "sim";

}
