package com.example.trend_project.apis.api.naver_news.service;

import com.example.trend_project.apis.client.MethodType;
import com.example.trend_project.apis.client.NaverClient;
import com.example.trend_project.apis.api.naver_news.dto.NewsResDto;
import com.example.trend_project.apis.handler.TextHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class NewsSearchService {

    private final RestTemplate restTemplate;
    private final NaverClient client;
    private final TextHandler textHandler;

    public NewsResDto newsSearch(String query) {
        URI uri = null;
        try {
            uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com/v1/search/news.json")
                    .queryParam("query", query)
                    .queryParam("sort", "sim")
                    .queryParam("display", 3)
                    .build()
                    .encode()
                    .toUri();

            var headers = client.createHeader(MethodType.Get);

            var reqEntity = RequestEntity.get(uri).headers(headers).build();

            var res = restTemplate.exchange(reqEntity, new ParameterizedTypeReference<NewsResDto>() {
            }).getBody();

            for (var r : res.getItems()) {
                r.setTitle(textHandler.textReplace(r.getTitle()));
            }

            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NewsResDto.builder().build();
    }
}
