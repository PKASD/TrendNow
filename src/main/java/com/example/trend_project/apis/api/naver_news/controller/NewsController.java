package com.example.trend_project.apis.api.naver_news.controller;

import com.example.trend_project.apis.api.naver_news.service.NewsSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/news")
public class NewsController {

    private final NewsSearchService newsSearchService;

    @GetMapping(path = "/search")
    public String showNews(@RequestParam String query, Model model) {
        var news = newsSearchService.newsSearch(query);
        model.addAttribute("news", news);
        return "blog/naver";
    }
}
