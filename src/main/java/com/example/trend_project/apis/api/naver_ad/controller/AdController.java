package com.example.trend_project.apis.api.naver_ad.controller;

import com.example.trend_project.apis.api.naver_ad.service.AdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SignatureException;

@Slf4j
@Controller
@RequestMapping("/ad")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;

    @GetMapping("/keyword")
    public String keyword(@RequestParam String keyword, Model model) throws SignatureException {
        log.info("keyword in ");
        var res = adService.getKeyword(keyword);
        model.addAttribute("user", res);
        return "main";
    }
}
