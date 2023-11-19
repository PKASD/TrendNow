package com.example.trend_project.apis.oauth.entity;

import com.example.trend_project.apis.oauth.entity.listener.Ilistener;
import com.example.trend_project.apis.oauth.entity.listener.TrendEntityListener;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

@Table(name = "user_history_t")
@EntityListeners(value = TrendEntityListener.class)
public class UserHistoryEntity implements Ilistener {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(nullable = false, length = 10)
    private String name;
    @Column(nullable = false, length = 100)
    private String email;
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_t_idx")
    private UserEntity user;

}
