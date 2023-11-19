package com.example.trend_project.apis.shopping_in_site.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class KeywordReqDto {
    private String startDate;
    private String endDate;
    private String timeUnit;
    private String category;
    @Builder.Default
    private List<Keyword> keyword = new ArrayList<>();
    private String device;
    private String gender;
    private List<String> ages;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Keyword {
        private String name;
        @Builder.Default
        private List<String> param = new ArrayList<>();

    }

}

