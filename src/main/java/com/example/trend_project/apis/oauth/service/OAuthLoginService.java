package com.example.trend_project.apis.oauth.service;

import com.example.trend_project.apis.client.KakaoClient;
import com.example.trend_project.apis.client.NaverClient;
import com.example.trend_project.apis.oauth.domain.OAuthProvider;
import com.example.trend_project.apis.oauth.dto.MyKeywordDto;
import com.example.trend_project.apis.oauth.dto.OAuthLoginRarams;
import com.example.trend_project.apis.oauth.dto.UserDto;
import com.example.trend_project.apis.oauth.dto.kakaoLoginDto.KakaoUserInfoDto;
import com.example.trend_project.apis.oauth.dto.naverLoginDto.NaverUserInfoDto;
import com.example.trend_project.apis.oauth.entity.UserEntity;
import com.example.trend_project.apis.oauth.repository.OAuthUserRepository;
import com.example.trend_project.apis.shopping_in_site.service.KeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthLoginService {

    @Value("${oauth.naver.url.auth}")
    private String authNaverUrl;
    @Value("${oauth.naver.redirect-url}")
    private String redirectNaverUrl;
    @Value("${oauth.naver.client-id}")
    private String clientNaverId;
    @Value("${oauth.naver.secret}")
    private String clientSecret;

    @Value("${oauth.kakao.url.auth}")
    private String authKakaoUrl;
    @Value("${oauth.kakao.redirect-url}")
    private String redirectKakaoUrl;
    @Value("${oauth.kakao.client-id}")
    private String clientKakaoId;

    private final NaverClient naverClient;
    private final KakaoClient kakaoClient;
    private final OAuthUserService OAuthUserService;
    private final MyKeywordService keywordService;


    public String getAuthUrl(OAuthProvider provider) throws Exception {

        switch (provider) {
            case NAVER:
            default:
                return UriComponentsBuilder.fromUriString(authNaverUrl+"/authorize")
                        .queryParam("response_type","code")
                        .queryParam("client_id",clientNaverId)
                        .queryParam("redirect_uri", URLEncoder.encode(redirectNaverUrl, "UTF-8"))
                        .queryParam("state",URLEncoder.encode("1234", "UTF-8"))
                        .build().toString();
            case KAKAO:
                return UriComponentsBuilder.fromUriString(authKakaoUrl+"/oauth/authorize")
                        .queryParam("response_type","code")
                        .queryParam("client_id",clientKakaoId)
                        .queryParam("redirect_uri", redirectKakaoUrl)
                        .build().encode().toString();
        }

    }

    public UserDto login(OAuthLoginRarams params, OAuthProvider provider) {
        switch (provider) {
            case NAVER:
                var naver =
                        naverClient.requestUserInfoWithAccessToken
                                (naverClient.requestAccessToken(params));

                return findOrCreateUser(naver);
            case KAKAO:
            default:
                var kakao =
                        kakaoClient.requestUserInfoWithAccessToken
                                (kakaoClient.requestAccessToken(params));
                return findOrCreateUser(kakao);
        }
    }

    private UserDto findOrCreateUser(NaverUserInfoDto infoDto) {
        log.info("naver sns info = {}", infoDto);
        var user
                = OAuthUserService.findByEmail(infoDto.getResponse().getEmail());

        if (user.getIdx() == null) {
            log.info("new user!");
            user.setName(infoDto.getResponse().getNickname());
            user.setEmail(infoDto.getResponse().getEmail());
            user = OAuthUserService.save(user);
        }

        return user;
    }

    private UserDto findOrCreateUser(KakaoUserInfoDto infoDto) {
        log.info("naver sns info = {}", infoDto);
        var user
                = OAuthUserService.findByEmail(infoDto.getKakaoAccount().getEmail());

        if (user.getIdx() == null) {
            log.info("new user!");
            user.setName(infoDto.getKakaoAccount().getProfile().getNickname());
            user.setEmail(infoDto.getKakaoAccount().getEmail());
            user = OAuthUserService.save(user);
        }

        return user;
    }

    public void updateUserKeywords(UserDto user, HttpSession session) {
        if (user != null) {
            List<MyKeywordDto> keywords = keywordService.getKeywordByUserId(user.getIdx());

            user.setKeywords(keywords.stream()
                    .map(MyKeywordDto::getKeyword)
                    .collect(Collectors.toList()));

            session.setAttribute("user", user);
        }
    }

}
