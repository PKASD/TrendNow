package com.example.trend_project.apis.shopping_in_site.service;

import com.example.trend_project.apis.client.MethodType;
import com.example.trend_project.apis.client.NaverClient;
import com.example.trend_project.apis.shopping_in_site.dto.CategoryReqDto;
import com.example.trend_project.apis.shopping_in_site.dto.CategoryResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final NaverClient client;

    @Value("${naver-apis.shopping-in-site.req-url}")
    private String reqUrl;
    @Value("${oauth.naver.client-id}")
    private String id;
    @Value("${oauth.naver.secret}")
    private String secret;

    private String path = "/categories";

    public CategoryResDto categoryReq(CategoryReqDto req) {
        try {
            var headers = client.createHeader(MethodType.Post);

            var reqEntity = RequestEntity.post(reqUrl + path).headers(headers).body(req);

            var resDto = new RestTemplate()
                    .exchange(reqEntity, new ParameterizedTypeReference<CategoryResDto>() {
                    }).getBody();

            return resDto;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return CategoryResDto.builder().build();
    }
}
