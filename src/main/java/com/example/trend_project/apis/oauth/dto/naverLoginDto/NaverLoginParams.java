package com.example.trend_project.apis.oauth.dto.naverLoginDto;

import com.example.trend_project.apis.oauth.dto.OAuthLoginRarams;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverLoginParams implements OAuthLoginRarams {
    private String code;
    private String state;

    private String error;
    @JsonProperty(value = "error_description")
    private String errorDescription;

    @Override
    public MultiValueMap<String, String> makeBody() {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("code", code);
        body.add("state", state);
        return body;
    }
}
