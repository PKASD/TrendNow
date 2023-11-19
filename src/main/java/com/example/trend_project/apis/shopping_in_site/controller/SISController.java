package com.example.trend_project.apis.shopping_in_site.controller;

import com.example.trend_project.apis.shopping_in_site.dto.*;
import com.example.trend_project.apis.shopping_in_site.service.CategoryService;
import com.example.trend_project.apis.shopping_in_site.service.KeywordGenderService;
import com.example.trend_project.apis.shopping_in_site.service.KeywordGenerationService;
import com.example.trend_project.apis.shopping_in_site.service.KeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/shopping")
@RequiredArgsConstructor
public class SISController {
    private final CategoryService categoryService;
    private final KeywordService keywordService;
    private final KeywordGenderService keywordGenderService;
    private final KeywordGenerationService keywordGenerationService;

    @PostMapping("/category")
    public CategoryResDto SISCategoryPost(@RequestBody CategoryReqDto req) {
        var response = categoryService.categoryReq(req);
        return response;
    }

    @PostMapping("/keyword")
    public KeywordResDto SISKeywordPost(@RequestBody KeywordReqDto req) {
        var response = keywordService.keywordReq(req);
        return response;
    }

    @PostMapping("/keyword/gender")
    public KeywordDetailResDto SISKeyGenderPost(@RequestBody KeywordDetailReqDto req) {
        var response = keywordGenderService.keywordReq(req);
        return response;
    }

    @PostMapping("/keyword/age")
    public KeywordDetailResDto SISKeyAgePost(@RequestBody KeywordDetailReqDto req) {
        var response = keywordGenerationService.keywordReq(req);
        return response;
    }
}
