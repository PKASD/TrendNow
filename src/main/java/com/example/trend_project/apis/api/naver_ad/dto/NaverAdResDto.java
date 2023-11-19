package com.example.trend_project.apis.api.naver_ad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NaverAdResDto {
    private List<KeywordList> KeywordList;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class KeywordList {
        private String relKeyword;
        private String monthlyPcQcCnt;
        private String monthlyMobileQcCnt;
        private String monthlyAvePcClkCnt;
        private String monthlyAveMobileClkCnt;
        private String monthlyAvePcCtr;
        private String monthlyAveMobileCtr;
        private String plAvgDepth;
        private String compIdx;
    }
}
