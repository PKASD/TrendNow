package com.example.trend_project.dto;

import com.example.trend_project.apis.oauth.entity.UserEntity;
import com.example.trend_project.entity.board.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentDto {
    private Long idx;
    @NotBlank
    @Size(min = 1, max = 30, message = "댓글의 길이는 1 ~ 50 글자입니다")
    private String context;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private UserEntity user;
    private BoardDto board;
}
