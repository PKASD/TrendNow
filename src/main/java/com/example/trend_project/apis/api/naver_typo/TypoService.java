package com.example.trend_project.apis.api.naver_typo;

import com.example.trend_project.apis.client.MethodType;
import com.example.trend_project.apis.client.NaverClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

@RequiredArgsConstructor
@Service
public class TypoService {
    private final RestTemplate restTemplate;
    private final NaverClient client;

    @Value(value = "${naver-apis.ad.typo.req-url}")
    private String url;

    public TypoResDto getTypoTrans(String query) {
        URI uri = null;
        try {
            try {
                uri = UriComponentsBuilder.fromUriString(url)
                        .queryParam("query", URLEncoder.encode(query, "UTF-8")).build().toUri();
            } catch (
                    UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            var headers = client.createHeader(MethodType.Get);

            var reqEntity = RequestEntity.get(uri).headers(headers).build();

            var res = restTemplate.exchange(reqEntity, new ParameterizedTypeReference<TypoResDto>() {
            }).getBody();

            return res;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return TypoResDto.builder().build();
    }
}
