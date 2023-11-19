package com.example.trend_project.service;

import com.example.trend_project.apis.oauth.dto.UserDto;
import com.example.trend_project.apis.oauth.repository.OAuthUserRepository;
import com.example.trend_project.apis.oauth.service.OAuthUserService;
import com.example.trend_project.dto.BoardDto;
import com.example.trend_project.dto.CommentDto;
import com.example.trend_project.entity.board.BoardEntity;
import com.example.trend_project.entity.board.CommentEntity;
import com.example.trend_project.repository.BoardRepository;
import com.example.trend_project.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final OAuthUserService userService;
    private final OAuthUserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private BoardEntity boardEntity;
    private BoardDto boardDto;

    public CommentDto save(CommentDto dto, UserDto userDto, Long boardNum) {
        var user = userRepository.findByEmail(userDto.getEmail()).orElseThrow();
        var findBoard = boardRepository.findByIdx(boardNum).get();

        dto.setBoard(boardService.entityToDto(findBoard));
        dto.setUser(user);

        boardDto = boardService.entityToDto(findBoard);
        boardEntity = findBoard;

        return entityToDto(commentRepository.save(dtoToEntity(dto)));
    }


    //현재 보드의 인덱스와 코멘트의 보드 인덱스 같으면 출력
    public List<CommentDto> getComments(Long boardNum) {
        List<CommentDto> comment = new ArrayList<>();
        //현재 인덱스의 보드
        var findBoard = boardService.findByIdx(boardNum);
        //현재 인덱스의 보드를 가지는 코멘트 리스트
        var findComment = commentRepository.findByBoard(boardService.dtoToEntity(findBoard)).orElseThrow();

        return findComment.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<CommentDto> findByBoard(BoardDto boardDto) {
        var commentOptional = commentRepository.findByBoard(boardService.dtoToEntity(boardDto));

        if (commentOptional.isPresent()) {
            return commentOptional.get().stream()
                    .map(this::entityToDto)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public CommentDto findByIdx(Long idx) {
        var entity = commentRepository.findByIdx(idx).orElse(null);
        return entityToDto(entity);
    }

    public void deleteByIdx(Long idx) {
        commentRepository.deleteByIdx(idx).orElseThrow();
    }

    public CommentEntity dtoToEntity(CommentDto dto) {
        var entity = CommentEntity.builder()
                .idx(dto.getIdx())
                .context(dto.getContext())
                .createdAt(dto.getCreatedAt())
                .updateAt(dto.getUpdateAt())
                .user(dto.getUser())
                .board(boardEntity)
                .build();

        return entity;
    }

    public CommentDto entityToDto(CommentEntity entity) {
        var dto = CommentDto.builder()
                .idx(entity.getIdx())
                .context(entity.getContext())
                .createdAt(entity.getCreatedAt())
                .updateAt(entity.getUpdateAt())
                .user(entity.getUser())
                .board(boardDto)
                .build();

        return dto;
    }
}
