package com.example.trend_project.apis.oauth.controller;

import com.example.trend_project.apis.oauth.dto.UserDto;
import com.example.trend_project.apis.oauth.service.OAuthLoginService;
import com.example.trend_project.apis.oauth.domain.OAuthProvider;
import com.example.trend_project.apis.oauth.dto.kakaoLoginDto.KakaoLoginParams;
import com.example.trend_project.apis.oauth.dto.naverLoginDto.NaverLoginParams;
import com.example.trend_project.apis.oauth.service.OAuthUserService;
import com.example.trend_project.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/oauth")
public class LoginController {
    private final OAuthLoginService oAuthLoginService;
    private final OAuthUserService userService;
    private final LoginInterceptor loginInterceptor;

    @GetMapping(path = "/naver/login")
    public void naverLogin(HttpServletResponse response) {
        log.info("naver login-");
        try {
            response.sendRedirect(oAuthLoginService.getAuthUrl(OAuthProvider.NAVER));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/kakao/login")
    public void kakaoLogin(HttpServletResponse response) {
        log.info("kakao login-");
        try {
            response.sendRedirect(oAuthLoginService.getAuthUrl(OAuthProvider.KAKAO));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @GetMapping(path = "/naver")
    public String callbackNaver(NaverLoginParams oAuthLoginRarams, Model model, HttpSession session) {
        log.info("code={}, state={}", oAuthLoginRarams.getCode(), oAuthLoginRarams.getState());

        var userDto = oAuthLoginService.login(oAuthLoginRarams, OAuthProvider.NAVER);
        //세션에 로그인 정보 저장
        session.setAttribute("user", userDto);
        session.getAttribute("user");
        oAuthLoginService.updateUserKeywords(userDto, session);
        model.addAttribute("user", userDto);

        return "redirect:/search";
    }

    @Transactional
    @GetMapping("/kakao")
    public String callbackKakao(KakaoLoginParams oAuthLoginRarams, Model model, HttpSession session) {
        log.info("code={}", oAuthLoginRarams.getCode());
        var userDto = oAuthLoginService.login(oAuthLoginRarams, OAuthProvider.KAKAO);

        //세션에 로그인 정보 저장
        session.setAttribute("user", userDto);
        session.getAttribute("user");
        oAuthLoginService.updateUserKeywords(userDto, session);
        log.info("keywords={}", userDto.getKeywords());
        model.addAttribute("user", userDto);

        return "redirect:/search";
    }

    //로그아웃 버튼 실행
    @GetMapping("/logout")
    public String logout(HttpServletResponse response, HttpSession session) {

        log.info("logout");

        session.invalidate();
        return "redirect:/login";
    }

}
