package com.example.trend_project.apis.oauth.dto;

import com.example.trend_project.apis.oauth.entity.MyKeywordEntity;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long idx;
    private String name;
    private String email;
    private String gender;
    private String ages;
    @Builder.Default
    private List<String> keywords = new ArrayList<>();
    private LocalDateTime createAt;

    public void addKeyword(String keyword) {
        if (keywords.size() < 5) keywords.add(keyword);
    }
}
