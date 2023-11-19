package com.example.trend_project.apis.oauth.entity.listener;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Slf4j
public class TrendEntityListener {

    @PreUpdate
    public void preUpdate(Object o){
        if (o instanceof Ilistener){
            ((Ilistener)o).setUpdateAt(LocalDateTime.now());
            log.info("---------Updating Done---------");
        }
    }

    @PrePersist
    public void prePersist(Object o){
        if (o instanceof Ilistener){
            ((Ilistener)o).setUpdateAt(LocalDateTime.now());
            ((Ilistener)o).setCreatedAt(LocalDateTime.now());
            log.info("-----------Persisting Done-----------");
        }
    }
}
