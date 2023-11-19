package com.example.trend_project.apis.handler;

import org.springframework.stereotype.Component;

@Component
public class TextHandler {
    public String textReplace(String text) {
        String cleanText = text.replaceAll("<.*?>", "").replaceAll("&[^;]+;", "");
        return cleanText;
    }
}
