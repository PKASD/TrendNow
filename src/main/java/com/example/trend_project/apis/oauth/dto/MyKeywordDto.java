package com.example.trend_project.apis.oauth.dto;

import com.example.trend_project.apis.oauth.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MyKeywordDto {
    private Long idx;
    private String keyword;
    private UserEntity user;
    private LocalDateTime createAt;
}
