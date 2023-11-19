package com.example.trend_project.service;

import com.example.trend_project.repository.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void tearDown() {
        System.out.println(commentRepository.findByIdx(36L).orElse(null));
    }
}