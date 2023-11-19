package com.example.trend_project.repository;

import com.example.trend_project.apis.oauth.dto.UserDto;
import com.example.trend_project.dto.BoardDto;
import com.example.trend_project.dto.CommentDto;
import com.example.trend_project.entity.board.BoardEntity;
import com.example.trend_project.entity.board.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> deleteByIdx(Long idx);
    Optional<CommentEntity> findByIdx(Long idx);

    Optional<List<CommentEntity>> findByBoard(BoardEntity board);
}
