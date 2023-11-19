package com.example.trend_project.controller;

import com.example.trend_project.apis.oauth.service.OAuthUserService;
import com.example.trend_project.dto.CommentDto;
import com.example.trend_project.service.BoardService;
import com.example.trend_project.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BoardController.class)
class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc; // MockMvc 주입
    @Autowired
    private OAuthUserService service;

    @MockBean
    private BoardService boardService;
    @MockBean
    private CommentService commentService; // CommentService의 MockBean 주입


    @Test
    void test(){
        var user = service.findByEmail("loves1543@naver.com");
    }
}