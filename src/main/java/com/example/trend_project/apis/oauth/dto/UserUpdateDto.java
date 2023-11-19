package com.example.trend_project.apis.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserUpdateDto {
    private String gender;
    private String ages;
}
