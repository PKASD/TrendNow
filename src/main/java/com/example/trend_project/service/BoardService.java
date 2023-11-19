package com.example.trend_project.service;

import com.example.trend_project.apis.oauth.dto.UserDto;
import com.example.trend_project.apis.oauth.entity.UserEntity;
import com.example.trend_project.apis.oauth.service.OAuthUserService;
import com.example.trend_project.dto.BoardDto;
import com.example.trend_project.entity.board.BoardEntity;
import com.example.trend_project.repository.BoardRepository;
import com.example.trend_project.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final GalleryRepository galleryRepository;
    private final OAuthUserService service;

    //게시글 저장 및 수정
    @Transactional
    public BoardDto save(BoardDto dto, UserDto userDto, String galleryName) {

        var gallery = galleryRepository.findByName(galleryName).orElseThrow();
        var findBoard = boardRepository.findByIdx(dto.getIdx());
        var user = dtoToEntity(service.findByEmail(userDto.getEmail()));

        //글 수정
        if (findBoard.isPresent()) {
            var updateBoard = findBoard.orElseThrow();
            updateBoard.setTitle(dto.getTitle());
            updateBoard.setContext(dto.getContext());

            return entityToDto(updateBoard);
        } else {
            var board = BoardDto.builder()
                    .title(dto.getTitle())
                    .context(dto.getContext())
                    .gallery(gallery)
                    .user(user)
                    .build();
            return entityToDto(boardRepository.save(dtoToEntity(board)));
        }

    }

    //게시글 삭제
    public void deleteById(Long idx) {
        boardRepository.deleteById(idx);
    }

    public BoardDto findByIdx(Long idx) {
        var find = boardRepository.findByIdx(idx).orElseThrow();
        var dto = entityToDto(find);
        return dto;
    }

    //제목으로 게시글 검색
    @Transactional
    public Page<BoardDto> findByTitleContaining(String galleryName, String title, Pageable pageable) {
        var finds = boardRepository.findByTitleContaining(title, pageable).orElse(null);
        finds.forEach(System.out::println);
        return finds.map(this::entityToDto);
    }

    //특정 게시판의 모든 게시글 조회
    public Page<BoardDto> findAll(String name, Pageable pageable) {
        var gal = galleryRepository.findByName(name).orElse(null);

        if (gal == null) {
            return Page.empty();
        }

        Page<BoardEntity> board = boardRepository.findByGallery(gal, pageable).orElse(null);
        board.forEach(System.out::println);

        return board.map(this::entityToDto);
    }

    public void IncViewCount(Long idx) {
        var find = boardRepository.findByIdx(idx).orElseThrow();
        find.setViewCount(find.getViewCount() + 1);

        boardRepository.save(find);
    }

    public void IncLikeCount(Long idx) {
        var find = boardRepository.findByIdx(idx).orElseThrow();
        find.setLikeCount(find.getLikeCount() + 1);

        boardRepository.save(find);
    }

    public BoardDto entityToDto(BoardEntity entity) {
        var dto = BoardDto.builder()
                .idx(entity.getIdx())
                .title(entity.getTitle())
                .context(entity.getContext())
                .createdAt(entity.getCreatedAt())
                .updateAt(entity.getUpdateAt())
                .gallery(entity.getGallery())
                .user(entity.getUser())
                .viewCount(entity.getViewCount())
                .likeCount(entity.getLikeCount())
                .comments(entity.getComments())
                .build();

        return dto;
    }

    public BoardEntity dtoToEntity(BoardDto dto) {
        var entity = BoardEntity.builder()
                .idx(dto.getIdx())
                .title(dto.getTitle())
                .context(dto.getContext())
                .createdAt(dto.getCreatedAt())
                .updateAt(dto.getUpdateAt())
                .gallery(dto.getGallery())
                .user(dto.getUser())
                .viewCount(dto.getViewCount())
                .likeCount(dto.getLikeCount())
                .comments(dto.getComments())
                .build();

        return entity;
    }

    private UserEntity dtoToEntity(UserDto dto) {
        var entity = UserEntity.builder()
                .idx(dto.getIdx())
                .name(dto.getName())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .ages(dto.getAges())
                .build();

        return entity;
    }

}
