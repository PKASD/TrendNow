package com.example.trend_project.apis.api.youtube.service;

import com.example.trend_project.apis.api.youtube.dto.YoutubeResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class YoutubeService {
    private final RestTemplate restTemplate;
    @Value("${youtube.api-key}")
    private String key;

    private final String url = "https://www.googleapis.com/youtube/v3/search";

    public YoutubeResDto getVideos(String p) {
        try {
            var params = createParams(p, key);

            var uri = UriComponentsBuilder.fromUriString(url)
                    .queryParams(params).build().encode().toUri();

            var req = RequestEntity.get(uri).build();

            var res = restTemplate.exchange(req, new ParameterizedTypeReference<YoutubeResDto>() {
            }).getBody();

            res.getItems().forEach(v -> {
                var id = v.getId().getVideoId();
                v.getId().setVideoId("https://www.youtube.com/watch?v=" + id);
            });

            return res;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return YoutubeResDto.builder().build();
    }

    public MultiValueMap<String, String> createParams(String p, String key) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("part", "snippet");
        params.add("maxResults", "3");
        params.add("q", p);
        params.add("type", "video");
        params.add("key", key);

        return params;
    }
}
