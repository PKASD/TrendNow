package com.example.trend_project.apis.oauth.entity.listener;

import java.time.LocalDateTime;

public interface Ilistener {

    void setCreatedAt(LocalDateTime localDateTime);
    void setUpdateAt(LocalDateTime localDateTime);
}
