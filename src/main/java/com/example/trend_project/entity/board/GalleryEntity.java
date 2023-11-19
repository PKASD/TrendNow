package com.example.trend_project.entity.board;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "gallery_t")
public class GalleryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true, nullable = false, length = 30)
    private String name;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "gallery", fetch = FetchType.LAZY)
    private List<BoardEntity> galleries = new ArrayList<>();

    public void addHistory(BoardEntity board) {
        this.galleries.add(board);
    }
}
