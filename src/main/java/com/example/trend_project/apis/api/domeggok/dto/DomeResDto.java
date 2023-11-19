package com.example.trend_project.apis.api.domeggok.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DomeResDto {
    private Domeggook domeggook;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Domeggook {
        private ResList list;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        @Builder
        public static class ResList {
            private List<Item> item;

            @NoArgsConstructor
            @AllArgsConstructor
            @Data
            @Builder
            public static class Item {
                private String title;
                private String thumb;
                private String url;
                private String price;
            }
        }
    }


}
