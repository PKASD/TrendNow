package com.example.trend_project.apis.client;

import com.example.trend_project.apis.api.naver_ad.util.Signatures;
import com.example.trend_project.apis.oauth.dto.OAuthLoginRarams;
import com.example.trend_project.apis.oauth.dto.naverLoginDto.NaverTokenDto;
import com.example.trend_project.apis.oauth.dto.naverLoginDto.NaverUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.beans.Encoder;
import java.security.SignatureException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class NaverClient {

    @Value("${oauth.naver.url.auth}")
    private String authUrl;
    @Value("${oauth.naver.redirect-url}")
    private String redirectUrl;
    @Value("${oauth.naver.client-id}")
    private String clientId;
    @Value("${oauth.naver.secret}")
    private String clientSecrect;

    @Value("${oauth.naver.url.api}")
    private String apiUrl;

    @Value("${naver-apis.ad.service-url}")
    private String serviceUrl;
    @Value("${naver-apis.ad.customer-id}")
    private String customerId;
    @Value("${naver-apis.ad.access-license}")
    private String license;
    @Value("${naver-apis.ad.secret}")
    private String secret;

    private final String path = "/keywordstool";

    private final RestTemplate restTemplate;

    public String requestAccessToken(OAuthLoginRarams params) {
        String url = authUrl + "/token";
        var body = params.makeBody();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecrect);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity entity = new HttpEntity<>(body, header);
        NaverTokenDto token = restTemplate.postForObject(url, entity, NaverTokenDto.class);

        assert token != null;

        System.out.println();
        return token.getAccessToken();

    }

    public NaverUserInfoDto requestUserInfoWithAccessToken(String accessToken) {
        String url = apiUrl + "/v1/nid/me";

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.set("Authorization", "Bearer " + accessToken);
        var body = new LinkedMultiValueMap<>();
        HttpEntity entity = new HttpEntity<>(body, header);
        NaverUserInfoDto info =
                restTemplate.postForObject(url, entity, NaverUserInfoDto.class);
        return info;
    }

    public HttpHeaders createHeader(MethodType type) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", clientId);
        headers.add("X-Naver-Client-Secret", clientSecrect);
        if (type.equals(MethodType.Post)) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        return headers;
    }

    public HttpHeaders createAdHeaders() throws SignatureException {
        var unixTime = String.valueOf(System.currentTimeMillis());
        var signature = Signatures.of(unixTime, "GET", path, secret);

        var headers = new HttpHeaders();
        headers.add("X-Timestamp", unixTime);
        headers.add("X-API-KEY", license);
        headers.add("X-API-SECRET", secret);
        headers.add("X-Customer", customerId);
        headers.add("X-Signature", signature);

        return headers;
    }

}
