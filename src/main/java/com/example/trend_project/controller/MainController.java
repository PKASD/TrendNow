package com.example.trend_project.controller;

import com.example.trend_project.apis.api.domeggok.service.DomeService;
import com.example.trend_project.apis.api.naver_ad.service.AdService;
import com.example.trend_project.apis.oauth.dto.UserDto;
import com.example.trend_project.apis.shopping_in_site.service.CategoryService;
import com.example.trend_project.apis.shopping_in_site.service.KeywordGenderService;
import com.example.trend_project.apis.shopping_in_site.service.KeywordGenerationService;
import com.example.trend_project.apis.shopping_in_site.service.KeywordService;
import com.example.trend_project.apis.api.youtube.service.YoutubeService;
import com.example.trend_project.apis.api.naver_blog.service.BlogSearchService;
import com.example.trend_project.apis.api.naver_news.service.NewsSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "")
public class MainController {

    private final BlogSearchService blogSearchService;
    private final NewsSearchService newsSearchService;
    private final DomeService domeService;
    private final AdService adService;
    private final YoutubeService youtubeService;

    @GetMapping(path = "/login")
    public String loginPage() {
        return "html/login"; // 로그인페이지로 이동
    }

    @GetMapping(path = "/search")
    public String home(Model model, HttpSession session) {
        var userDto = session.getAttribute("user");
        model.addAttribute("user", userDto);

        return "html/search";
    }

    @GetMapping(path = "/board")
    public String board() {
        return "html/board"; // 게시판으로 이동
    }

    @GetMapping(path = "/interst-keyword")
    public String interKeyword() {
        return "html/interest-keyword"; // 관심사 등록 페이지
    }

    @GetMapping(path = "/mini-game1")
    public String miniGame1() {
        return "html/mini-game1";
    }

    @GetMapping(path = "/mini-game2")
    public String miniGame2() {
        return "html/mini-game2";
    }

    @GetMapping(path = "/mini-game3")
    public String miniGame3() {
        return "html/mini-game3";
    }

    @GetMapping(path = "/result")
    public String getResult(@RequestParam String keyword, Model model, HttpSession session) {

        log.info("Received keyword: {}", keyword);

        //keyword 값이 이상하면 메인 페이지로
        if (keyword == null || keyword.isEmpty()) {
            return "html/search";
        }

        var blogList = blogSearchService.search(keyword);
        var newsList = newsSearchService.newsSearch(keyword);
        var itemList = domeService.getItemList(keyword);
        var adList = adService.getKeyword(keyword);
        var videoList = youtubeService.getVideos(keyword);
        var userDto = (UserDto) session.getAttribute("user");

        model.addAttribute("blogs", blogList)
                .addAttribute("news", newsList)
                .addAttribute("items", itemList)
                .addAttribute("ads", adList)
                .addAttribute("videos", videoList)
                .addAttribute("searchKeyword", keyword)
                .addAttribute("user", userDto);

        return "html/result";
    }

}
