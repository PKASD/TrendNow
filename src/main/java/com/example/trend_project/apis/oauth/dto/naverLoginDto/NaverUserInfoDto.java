package com.example.trend_project.apis.oauth.dto.naverLoginDto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NaverUserInfoDto {

    private Response response;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Response {
        private String nickname;
        private String email;
    }
}
