package com.example.trend_project.apis.api.naver_blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BlogResDto {
    private String lastBuildDate;
    private int total;
    private String start;
    private String display;
    @Builder.Default
    private List<Item> items = new ArrayList<>();

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Item {
        private String title;
        private String link;
        private String description;
        @JsonProperty("bloggername")
        private String bloggerName;
        @JsonProperty("bloggerlink")
        private String bloggerLink;
        private String postdate;

    }
}