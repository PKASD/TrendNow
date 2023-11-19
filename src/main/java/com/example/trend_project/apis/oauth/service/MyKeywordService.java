package com.example.trend_project.apis.oauth.service;

import com.example.trend_project.apis.oauth.dto.MyKeywordDto;
import com.example.trend_project.apis.oauth.dto.UserDto;
import com.example.trend_project.apis.oauth.entity.MyKeywordEntity;
import com.example.trend_project.apis.oauth.entity.UserEntity;
import com.example.trend_project.apis.oauth.repository.MyKeywordRepository;
import com.example.trend_project.apis.oauth.repository.OAuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyKeywordService {

    private final MyKeywordRepository keywordRepository;
    private final OAuthUserRepository userRepository;

    @Transactional
    public UserDto addMyKeyword(MyKeywordDto keywordDto, UserDto userDto, String keyword) {
        var person = userRepository.findById(userDto.getIdx()).orElseThrow();
        //한 유저 당 들어갈 수 있는 키워드는 5개 / DB에 유저 정보와 키워드가 저장됨
        if (person.getKeywords().size() < 5) {
            var keywordDtoWithUser = MyKeywordDto.builder()
                    .idx(keywordDto.getIdx())
                    .user(person)
                    .keyword(keyword)
                    .createAt(keywordDto.getCreateAt())
                    .build();

            var myKeyword = entityToDto(keywordRepository.save(dtoToEntity(keywordDtoWithUser)));
            //userDto에 List<MyKeywordDto>가 저장됨
            userDto.addKeyword(myKeyword.getKeyword());

            return userDto;
        }
        return null;
    }

    @Transactional
    public boolean deleteKeyword(UserDto userDto, int keywordIndex) {
        UserEntity userEntity = userRepository.findById(userDto.getIdx()).orElse(null);

        if (userEntity != null) {
            List<MyKeywordEntity> keywords = userEntity.getKeywords();

            if (keywordIndex >= 0 && keywordIndex < keywords.size()) {
                MyKeywordEntity deletedKeyword = keywords.remove(keywordIndex);
                keywordRepository.delete(deletedKeyword);

                userRepository.save(userEntity); // 변경된 userEntity 저장

                return true;
            }
        }

        return false;
    }

    public MyKeywordDto entityToDto(MyKeywordEntity entity) {
        var dto = MyKeywordDto.builder()
                .idx(entity.getIdx())
                .keyword(entity.getKeyword())
                .user(entity.getUser())
                .createAt(entity.getCreatedAt())
                .build();

        return dto;
    }

    public MyKeywordEntity dtoToEntity(MyKeywordDto dto) {
        var entity = MyKeywordEntity.builder()
                .user(dto.getUser())
                .keyword(dto.getKeyword())
                .createdAt(dto.getCreateAt())
                .build();

        return entity;
    }

    @Transactional
    public List<MyKeywordDto> getKeywordByUserId(Long idx) {
        var keywordEntities = userRepository.findById(idx).orElseThrow().getKeywords();

         return keywordEntities.stream()
                 .map(this::entityToDto)
                 .collect(Collectors.toList());
    }

    public List<MyKeywordDto> findAll() {
        return keywordRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
