package com.example.trend_project.apis.oauth.repository;


import com.example.trend_project.apis.oauth.entity.UserHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OAuthUserHistoryRepository
        extends JpaRepository<UserHistoryEntity, Long> {

    Optional<List<UserHistoryEntity>>findByUserIdx(Long idx);
}
