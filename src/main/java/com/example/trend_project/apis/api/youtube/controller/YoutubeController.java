package com.example.trend_project.apis.api.youtube.controller;

import com.example.trend_project.apis.api.youtube.service.YoutubeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/youtube")
@RequiredArgsConstructor
public class YoutubeController {
    private final YoutubeService youtubeService;
    @GetMapping("/search")
    public String youtubeSearch(@RequestParam String p, Model model){
        var res = youtubeService.getVideos(p);
        model.addAttribute("user",res);
        return "main";
    }
}
