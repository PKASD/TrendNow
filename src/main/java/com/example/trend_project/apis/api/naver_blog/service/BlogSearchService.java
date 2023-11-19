package com.example.trend_project.apis.api.naver_blog.service;

import com.example.trend_project.apis.client.MethodType;
import com.example.trend_project.apis.client.NaverClient;
import com.example.trend_project.apis.api.naver_blog.dto.BlogResDto;
import com.example.trend_project.apis.handler.TextHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class BlogSearchService {
    private final NaverClient naveClient;
    private final RestTemplate restTemplate;
    private final TextHandler textHandler;

    public BlogResDto search(String query) {
        try {
            var uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com/v1/search/blog.json")
                    .queryParam("query", query)
                    .queryParam("display", 3)
                    .build().encode().toUri();

            var headers = naveClient.createHeader(MethodType.Get);

            var reqEntity = RequestEntity.get(uri).headers(headers).build();

            var res = restTemplate.exchange(reqEntity, new ParameterizedTypeReference<BlogResDto>() {
            }).getBody();

            for (var r : res.getItems()) {
                r.setTitle(textHandler.textReplace(r.getTitle()));
            }

            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BlogResDto.builder().build();

    }
}
