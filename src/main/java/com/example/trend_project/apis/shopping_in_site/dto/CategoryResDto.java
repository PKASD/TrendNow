package com.example.trend_project.apis.shopping_in_site.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryResDto {
    private String startDate;
    private String endDate;
    private String timeUnit;
    private List<Result> results;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Result {
        private String title;
        private List<String> category;
        private List<ResData> data;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        @Builder
        public static class ResData {
            private String period;
            // null이 들어오면 안되니 Double가 아닌 double
            private double ratio;
        }
    }
}
