package com.example.trend_project.service;

import com.example.trend_project.apis.oauth.dto.UserDto;
import com.example.trend_project.apis.oauth.entity.UserEntity;
import com.example.trend_project.apis.oauth.service.OAuthUserService;
import com.example.trend_project.dto.BoardDto;
import com.example.trend_project.dto.GalleryDto;
import com.example.trend_project.entity.board.BoardEntity;
import com.example.trend_project.entity.board.GalleryEntity;
import com.example.trend_project.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@SpringBootTest
class BoardServiceTest {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private OAuthUserService service;
    @Autowired
    private GalleryService galleryService;

    @Rollback(value = false)
    @Test
    @Transactional
    void test() {
       var gallery = GalleryEntity.builder().name("자유게시판").build();
       galleryService.save(entityToDto(gallery));

//        var user = UserEntity.builder()
//                .name("이름")
//                .email("email4@gamil.com")
//                .build();*/
//
//        var findGallery = galleryService.findByName("자유게시판");
//        var user = service.findByEmail("loves1543@naver.com");
//
//        for (int i=0;i<30;i++){
//            var entity = BoardEntity.builder()
//                    .title("제목"+i)
//                    .context("내용"+i)
//                    .build();
//            boardService.save(entityToDto(entity), user, "자유게시판");

//        }
    }

    private GalleryDto entityToDto(GalleryEntity entity) {
        var dto = GalleryDto.builder()
                .idx(entity.getIdx())
                .name(entity.getName())
                .build();

        return dto;
    }

    private GalleryEntity dtoToEntity(GalleryDto dto) {
        var entity = GalleryEntity.builder()
                .idx(dto.getIdx())
                .name(dto.getName())
                .build();

        return entity;
    }

    private UserDto entityToDto(UserEntity entity) {
        var dto = UserDto.builder()
                .idx(entity.getIdx())
                .name(entity.getName())
                .email(entity.getEmail())
                .gender(String.valueOf(entity.getGender()))
                .createAt(entity.getCreatedAt())
                .build();

        return dto;
    }

    private UserEntity dtoToEntity(UserDto dto) {
        var entity = UserEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .build();

        return entity;
    }

    private BoardDto entityToDto(BoardEntity entity) {
        var dto = BoardDto.builder()
                .idx(entity.getIdx())
                .title(entity.getTitle())
                .context(entity.getContext())
                .createdAt(entity.getCreatedAt())
                .updateAt(entity.getUpdateAt())
                .gallery(entity.getGallery())
                .user(entity.getUser())
                .comments(entity.getComments())
                .build();

        return dto;
    }

    private BoardEntity dtoToEntity(BoardDto dto) {
        var entity = BoardEntity.builder()
                .idx(dto.getIdx())
                .title(dto.getTitle())
                .context(dto.getContext())
                .createdAt(dto.getCreatedAt())
                .updateAt(dto.getUpdateAt())
                .gallery(dto.getGallery())
                .user(dto.getUser())
                .comments(dto.getComments())
                .build();

        return entity;
    }

}