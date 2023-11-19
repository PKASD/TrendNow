package com.example.trend_project.apis.api.domeggok.service;

import com.example.trend_project.apis.api.domeggok.dto.DomeResDto;
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

@RequiredArgsConstructor
@Service
@Slf4j
public class DomeService {
    @Value("${domeggook.api-key}")
    private String key;
    @Value("${domeggook.service-url}")
    private String url;
    private final RestTemplate restTemplate;

    public DomeResDto getItemList(String kw) {
        try {

            var params = getParams(kw);

            var uri = UriComponentsBuilder.fromUriString(url)
                    .queryParams(params)
                    .build().toUri();
            var req = RequestEntity.get(uri).build();

            var res = restTemplate.exchange(req, new ParameterizedTypeReference<DomeResDto>() {
            }).getBody();

            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DomeResDto.builder().build();
    }

    public MultiValueMap<String, String> getParams(String kw) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ver", "4.1");
        params.add("mode", "getItemList");
        params.add("aid", key);
        params.add("market", "dome");
        params.add("om", "json");
        params.add("kw", kw);
        params.add("sz", "5");

        return params;
    }
}
