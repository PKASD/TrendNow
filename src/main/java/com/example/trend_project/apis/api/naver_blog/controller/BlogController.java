package com.example.trend_project.apis.api.naver_blog.controller;

import com.example.trend_project.apis.api.naver_blog.service.BlogSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Slf4j
@Controller
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogSearchService blogSearchService;

    @GetMapping("/search")
    public String getSearch(@RequestParam String query, Model model) throws UnsupportedEncodingException {
        log.info("blog in---");
        var res = blogSearchService.search(query);
        model.addAttribute("user", res);

        return "blog/naver";
    }
}
