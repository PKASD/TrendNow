package com.example.trend_project.entity.board;

import com.example.trend_project.apis.oauth.entity.UserEntity;
import com.example.trend_project.apis.oauth.entity.listener.Ilistener;
import com.example.trend_project.apis.oauth.entity.listener.TrendEntityListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "comment_t")
@EntityListeners(value = TrendEntityListener.class)
public class CommentEntity implements Ilistener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(nullable = false, length = 10)
    private String context;
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_t_idx")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_t_idx")
    private BoardEntity board;
}
