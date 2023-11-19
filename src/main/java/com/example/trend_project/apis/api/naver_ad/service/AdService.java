package com.example.trend_project.apis.api.naver_ad.service;

import com.example.trend_project.apis.api.naver_ad.dto.NaverAdResDto;
import com.example.trend_project.apis.client.NaverClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.SignatureException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdService {
    private final RestTemplate restTemplate;
    private final NaverClient naverClient;

    @Value("${naver-apis.ad.service-url}")
    private String serviceUrl;

    private final String path = "/keywordstool";


    public NaverAdResDto.KeywordList getKeyword(String keyword) {
        try {

            var uri = UriComponentsBuilder.fromUriString(serviceUrl + path).queryParams(createParams(keyword)).build().toString();

            RequestEntity<Void> req = null;
            try {
                req = RequestEntity.get(uri).headers(naverClient.createAdHeaders()).build();
            } catch (SignatureException e) {
                throw new RuntimeException(e);
            }

            var res = restTemplate.exchange(req, new ParameterizedTypeReference<NaverAdResDto>() {
            }).getBody();

            return res.getKeywordList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NaverAdResDto.KeywordList.builder().build();
    }

    public MultiValueMap<String, String> createParams(String keyword) {
        String showDetails = "1";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("hintKeywords", keyword);
/*
        try {
            params.add("hintKeywords", URLEncoder.encode(keyword, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            //예외처리 던지기
            //throw new RuntimeException(e);
            //예외 발생 원인 위치 식별
            e.printStackTrace();
        }*/
        params.add("showDetail", showDetails);

        return params;
    }
}