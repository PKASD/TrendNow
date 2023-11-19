package com.example.trend_project.repository;

import com.example.trend_project.apis.oauth.entity.UserEntity;
import com.example.trend_project.entity.board.BoardEntity;
import com.example.trend_project.entity.board.GalleryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
    Optional<Page<BoardEntity>> findByTitleContaining(String title,Pageable pageable);
    Optional<BoardEntity> findByIdx(Long idx);
    Optional<Page<BoardEntity>> findByGallery(GalleryEntity gallery, Pageable pageable);

    Optional<BoardEntity> findByUser(UserEntity user);

    void deleteById(Long idx);

    List<BoardEntity> findAll();
}
