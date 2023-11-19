package com.example.trend_project.apis.oauth.dto;

import org.springframework.util.MultiValueMap;

//for kakao 다형성 활용을 위해
public interface OAuthLoginRarams {
    MultiValueMap<String, String> makeBody();
}
