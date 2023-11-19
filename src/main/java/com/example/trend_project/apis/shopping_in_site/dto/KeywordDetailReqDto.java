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
public class KeywordDetailReqDto {
    private String startDate;
    private String endDate;
    private String timeUnit;
    private String category;
    private String keyword;
    private String device;
    private String gender;
    private List<String> ages;


}

