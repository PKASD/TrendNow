package com.example.trend_project.apis.oauth.entity.listener;

import com.example.trend_project.apis.oauth.beansUtil.BeansUtils;
import com.example.trend_project.apis.oauth.entity.UserEntity;
import com.example.trend_project.apis.oauth.entity.UserHistoryEntity;
import com.example.trend_project.apis.oauth.repository.OAuthUserHistoryRepository;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PostPersist;
import javax.persistence.PreUpdate;

@Slf4j
public class HistoryListener {

    @PostPersist
    @PreUpdate
    public void preUpdateAndPostPersist(Object o){

        var user = (UserEntity) o;
        var history = UserHistoryEntity.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .user(user)
                        .build();

        OAuthUserHistoryRepository historyRepository =
                BeansUtils.getBean(OAuthUserHistoryRepository.class);
        historyRepository.save(history);
    }
}
