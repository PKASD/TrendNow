package com.example.trend_project.apis.oauth.repository;

import com.example.trend_project.apis.oauth.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface OAuthUserRepository
        extends JpaRepository<UserEntity, Long> {

    //case 1 : find the element 1 or arrays
    List<UserEntity> findByName(String name);
    Optional<UserEntity> findByEmail(String email);
    Optional<List<UserEntity>> findTop2ByName(String name);
    UserEntity findTop1ByEmail(String email);

    //case 2 : use and | or and find 2 more elements
    List<UserEntity> findByNameAndEmail(String name, String email);
    List<UserEntity> findByNameOrEmail(String name, String email);
    List<UserEntity> findByCreatedAtAfter(LocalDateTime localDateTime);

    //GreaterThan/LessThanEqual/IsNotNull/StartingWith
//    List<UserEntity> findByIdxGreaterThan(Long idx);
//    List<UserEntity> findByIdxLessThanEqual(Long idx);
//    List<UserEntity> findByEmailIsNotNull();
    List<UserEntity> findByEmailStartingWith(String email);

    //Q1. 특정 사용자 이름 2개를 쿼리
    List<UserEntity> findByNameIn(List<String> names);

    //Q2. 특정 아이디 2~4사이의 사용자 쿼리
    List<UserEntity> findByIdxBetween(Long idx1, Long idx2);

    //Q3. 생성일이 특정 날짜 사이에포함되는 사용자 쿼리
    List<UserEntity> findByCreatedAtBetween(LocalDateTime localDateTime1, LocalDateTime localDateTime2);

    List<UserEntity> findByNameOrderByIdxDesc(String name);
    List<UserEntity> findByName(String name, Sort sort);
    Page<UserEntity> findByCreatedAtAfter(LocalDateTime localDateTime, Pageable pageable);

}
