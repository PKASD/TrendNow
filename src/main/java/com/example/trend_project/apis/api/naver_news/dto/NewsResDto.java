package com.example.trend_project.apis.api.naver_news.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class NewsResDto {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    @Builder.Default
    private List<Item> items = new ArrayList<>();


    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @Builder
    public static class Item {
        private String title;
        @JsonProperty(value = "originallink")
        private String originalLink;
        private String link;
        private String description;
        private String pubDate;
    }
}
