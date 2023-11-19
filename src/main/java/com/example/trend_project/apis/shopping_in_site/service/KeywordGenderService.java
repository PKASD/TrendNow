package com.example.trend_project.apis.shopping_in_site.service;

import com.example.trend_project.apis.client.MethodType;
import com.example.trend_project.apis.client.NaverClient;
import com.example.trend_project.apis.shopping_in_site.dto.KeywordDetailReqDto;
import com.example.trend_project.apis.shopping_in_site.dto.KeywordDetailResDto;
import com.example.trend_project.apis.shopping_in_site.dto.KeywordResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class KeywordGenderService {
    private final RestTemplate restTemplate;
    private final NaverClient client;
    @Value("${oauth.naver.client-id}")
    private String id;
    @Value("${oauth.naver.secret}")
    private String secret;
    @Value("${naver-apis.shopping-in-site.req-url}")
    private String reqUrl;

    private final String path = "/category/keyword/gender";

    public KeywordDetailResDto keywordReq(KeywordDetailReqDto req) {
        try {
            var headers = client.createHeader(MethodType.Post);

            var reqEntity = new HttpEntity<>(req, headers);

            var resDto = restTemplate.postForObject(reqUrl + path, reqEntity, KeywordDetailResDto.class);

            return resDto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return KeywordDetailResDto.builder().build();
    }
}

