package com.example.trend_project.apis.api.naver_typo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/typo")
public class TypoController {

    private final TypoService typoService;

    @GetMapping("/trans")
    public void typo(@RequestParam String query) {
        var res = typoService.getTypoTrans(query);

        if (res.toString().isEmpty()){
            log.info("res null");
        }else if(res.toString()==""){
            log.info("res은 공백");
        }
    }
}
