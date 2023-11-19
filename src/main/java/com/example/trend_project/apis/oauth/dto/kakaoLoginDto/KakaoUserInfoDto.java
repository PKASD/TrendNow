package com.example.trend_project.apis.oauth.dto.kakaoLoginDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class KakaoUserInfoDto {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount {
        private KakaoProfile profile;
        private String email;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoProfile {
        private String nickname;
    }
}
