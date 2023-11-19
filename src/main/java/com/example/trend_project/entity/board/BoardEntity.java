package com.example.trend_project.entity.board;

import com.example.trend_project.apis.oauth.entity.UserEntity;
import com.example.trend_project.apis.oauth.entity.listener.Ilistener;
import com.example.trend_project.apis.oauth.entity.listener.TrendEntityListener;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "board_t")
@EntityListeners(value = {TrendEntityListener.class})
public class BoardEntity implements Ilistener {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 500)
    private String context;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private int viewCount;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_t_idx")
    private GalleryEntity gallery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_t_idx")
    private UserEntity user;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<CommentEntity> comments = new ArrayList<>();

    public void addComments(CommentEntity comment) {
        this.comments.add(comment);
    }


}
