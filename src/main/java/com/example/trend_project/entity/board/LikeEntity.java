package com.example.trend_project.entity.board;

import com.example.trend_project.apis.oauth.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "like_t")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_t_idx")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_t_idx")
    private BoardEntity board;
}
