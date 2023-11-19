package com.example.trend_project.apis.api.naver_blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BlogReqDto {
    private String query;
    @Builder.Default
    private int display = 10;
    @Builder.Default
    private int start = 1;
    private String sort;

}
