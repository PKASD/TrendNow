package com.example.trend_project.apis.oauth.entity;

import com.example.trend_project.apis.oauth.entity.listener.Ilistener;
import com.example.trend_project.apis.oauth.entity.listener.TrendEntityListener;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "keywords_t")
@EntityListeners(value = TrendEntityListener.class)
public class MyKeywordEntity implements Ilistener {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column
    private String keyword;
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "user_t_idx")
    private UserEntity user;
}
