package com.example.trend_project.apis.oauth.entity;

import com.example.trend_project.apis.oauth.entity.listener.HistoryListener;
import com.example.trend_project.apis.oauth.entity.listener.Ilistener;
import com.example.trend_project.apis.oauth.entity.listener.TrendEntityListener;
import com.example.trend_project.entity.board.BoardEntity;
import com.example.trend_project.entity.board.CommentEntity;
import com.example.trend_project.entity.board.LikeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "user_t")
@Builder
@ToString
@EntityListeners(value = {TrendEntityListener.class, HistoryListener.class})
public class UserEntity implements Ilistener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(nullable = false, length = 10)
    private String name;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updateAt;
    @Column
    private String gender;
    @Column
    private String ages;

    //new arrayList는 초기화하는게 안정적
    @JsonIgnore
    @Builder.Default
    @ToString.Exclude
    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserHistoryEntity> histories = new ArrayList<>();
    public void addHistory(UserHistoryEntity history){
        this.histories.add(history);
    }

    @JsonIgnore
    @Builder.Default
    @ToString.Exclude
    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY)
    private List<LikeEntity> likes = new ArrayList<>();
    public void addLikes(LikeEntity like){
        this.likes.add(like);
    }

    @JsonIgnore
    @Builder.Default
    @ToString.Exclude
    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY)
    private List<BoardEntity> boards = new ArrayList<>();
    public void addBoards(BoardEntity board){
        this.boards.add(board);
    }

    @JsonIgnore
    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<CommentEntity> comments = new ArrayList<>();

    public void addComments(CommentEntity comment) {
        this.comments.add(comment);
    }

    @JsonIgnore
    @Builder.Default
    @ToString.Exclude
    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<MyKeywordEntity>  keywords = new ArrayList<>();

    public void addKeywords(MyKeywordEntity keyword){
        this.keywords.add(keyword);
    }

}
