package com.example.trend_project.repository;

import com.example.trend_project.entity.board.GalleryEntity;
import com.example.trend_project.entity.board.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity,Long> {
    Optional<LikeEntity> findByIdx(Long idx);

    void deleteById(Long idx);
}
