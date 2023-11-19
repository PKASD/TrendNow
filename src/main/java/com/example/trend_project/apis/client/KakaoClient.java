package com.example.trend_project.apis.client;

import com.example.trend_project.apis.oauth.dto.OAuthLoginRarams;
import com.example.trend_project.apis.oauth.dto.kakaoLoginDto.KakaoTokenDto;
import com.example.trend_project.apis.oauth.dto.kakaoLoginDto.KakaoUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoClient {

    @Value("${oauth.kakao.url.auth}")
    private String authUrl;
    @Value("${oauth.kakao.client-id}")
    private String clientId;
    @Value("${oauth.kakao.url.api}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public String requestAccessToken(OAuthLoginRarams params) {
        String url = authUrl + "/oauth/token";
        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity entity = new HttpEntity<>(body, header);
        KakaoTokenDto token = restTemplate.postForObject(url, entity, KakaoTokenDto.class);

        assert token!=null;
        System.out.println(token.getAccessToken());
        return token.getAccessToken();

    }

    public KakaoUserInfoDto requestUserInfoWithAccessToken(String accessToken) {
        String url = apiUrl + "/v2/user/me";

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.set("Authorization", "Bearer "+accessToken);
        var body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");
        HttpEntity entity = new HttpEntity<>(body, header);
        KakaoUserInfoDto info =
                restTemplate.postForObject(url, entity, KakaoUserInfoDto.class);
        return info;
    }


}
