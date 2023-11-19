package com.example.trend_project.apis.api.domeggok.controller;

import com.example.trend_project.apis.api.domeggok.service.DomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/dome")
@Slf4j
public class DomeController {
    private final DomeService domeService;

    @GetMapping("/search")
    public String search(@RequestParam String kw, Model model) {
        var res = domeService.getItemList(kw);
        model.addAttribute("user", res);
        return "test";
    }

}
