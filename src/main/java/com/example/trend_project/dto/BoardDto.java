package com.example.trend_project.dto;

import com.example.trend_project.apis.oauth.entity.UserEntity;
import com.example.trend_project.entity.board.CommentEntity;
import com.example.trend_project.entity.board.GalleryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
@Builder
public class BoardDto {
    private Long idx;
    @NotBlank
    @Size(min = 1, max = 50, message = "제목의 길이는 1 ~ 50 글자입니다")
    private String title;
    @NotBlank
    @Size(min = 1, max = 500, message = "본문의 길이는 1 ~ 500 글자입니다")
    private String context;
    private int likeCount;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private GalleryEntity gallery;
    private UserEntity user;
    @Builder.Default
    private List<CommentEntity> comments = new ArrayList<>();
}
