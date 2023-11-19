package com.example.trend_project.repository;

import com.example.trend_project.entity.board.BoardEntity;
import com.example.trend_project.entity.board.GalleryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GalleryRepository extends JpaRepository<GalleryEntity, Long> {

    Optional<GalleryEntity> findByName(String name);

    void deleteById(Long idx);
}
