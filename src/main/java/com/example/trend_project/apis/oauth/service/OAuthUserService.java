package com.example.trend_project.apis.oauth.service;

import com.example.trend_project.apis.oauth.dto.MyKeywordDto;
import com.example.trend_project.apis.oauth.entity.MyKeywordEntity;
import com.example.trend_project.apis.oauth.entity.UserEntity;
import com.example.trend_project.apis.oauth.dto.UserDto;
import com.example.trend_project.apis.oauth.repository.OAuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthUserService {
    private final OAuthUserRepository OAuthUserRepository;


    //repository에 저장하기 위해 dto를 먼저 entity로 저장
    //controller에 올리기위해선 다시 entity를 dto로 바꿔야함
    public UserDto save(UserDto dto) {
        return entityToDto(OAuthUserRepository.save(dtoToEntity(dto)));
    }

    public UserDto entityToDto(UserEntity entity){
        var dto = UserDto.builder()
                .idx(entity.getIdx())
                .name(entity.getName())
                .email(entity.getEmail())
                .gender(entity.getGender())
                .ages(entity.getAges())
                .createAt(entity.getCreatedAt())
                .build();

        return dto;
    }

    //Html에 필요한 정보만 entity로 바까줌
    private UserEntity dtoToEntity(UserDto dto) {
        var entity = UserEntity.builder()
                .idx(dto.getIdx())
                .name(dto.getName())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .ages(dto.getAges())
                .createdAt(dto.getCreateAt())
                .build();

        return entity;
    }

//    public UserDto saveInform(UserDto userDto) {
//        var result = UserDto.builder()
//                .idx(userDto.getIdx())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .createAt(userDto.getCreateAt())
//                .build();
//        return result;
//    }

    public UserDto findByEmail(String email) {
        var user = OAuthUserRepository.findByEmail(email);
        if (user.isPresent()) return entityToDto(user.get());
        else return UserDto.builder().build();
    }


}
