package com.example.trend_project.apis.oauth.dto.kakaoLoginDto;

import com.example.trend_project.apis.oauth.dto.OAuthLoginRarams;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class KakaoLoginParams implements OAuthLoginRarams {
    private String code;

    @Override
    public MultiValueMap<String, String> makeBody() {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("code", code);
        return body;
    }
}
