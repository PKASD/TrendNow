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
public class CategoryReqDto {
    private String startDate;
    private String endDate;
    private String timeUnit;
    @Builder.Default
    private List<Category> category = new ArrayList<>();
    private String device;
    private String gender;
    private List<String> ages;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Category {
        private String name;
        @Builder.Default
        private List<String> param = new ArrayList<>();

    }

}

