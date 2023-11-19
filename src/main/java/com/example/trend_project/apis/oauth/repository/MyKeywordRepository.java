package com.example.trend_project.apis.oauth.repository;

import com.example.trend_project.apis.oauth.entity.MyKeywordEntity;
import com.example.trend_project.apis.oauth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyKeywordRepository extends JpaRepository<MyKeywordEntity, Long> {
    List<MyKeywordEntity> findByUser (UserEntity idx);
}
