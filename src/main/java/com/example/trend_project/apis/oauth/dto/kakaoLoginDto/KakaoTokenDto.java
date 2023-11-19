package com.example.trend_project.apis.oauth.dto.kakaoLoginDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KakaoTokenDto {
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
    @JsonProperty(value = "token_type")
    private String tokenType;
    @JsonProperty(value = "expires_in")
    private int expiresIn;
    @JsonProperty("refresh_token_expires_in")
    private String refreshTokenExpiresIn;
    @JsonProperty("scope")
    private String scope;
}
