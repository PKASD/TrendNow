package com.example.trend_project.apis.api.youtube.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class YoutubeResDto {
    private List<Item> items;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Item {
        private Snippet snippet;
        private Id id;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        @Builder
        public static class Id {
            private String videoId;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        @Builder
        public static class Snippet {
            private String title;
            private String description;
            private Thumbnails thumbnails;
            private String channelTitle;

            @NoArgsConstructor
            @AllArgsConstructor
            @Data
            @Builder
            public static class Thumbnails {
                @JsonProperty("high")
                private Thumbnail highThumbnail;

                @NoArgsConstructor
                @AllArgsConstructor
                @Data
                @Builder
                public static class Thumbnail {
                    private String url;
                }
            }
        }
    }
}
