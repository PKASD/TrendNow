package com.example.trend_project.apis.oauth.repository;

import com.example.trend_project.apis.oauth.dto.MyKeywordDto;
import com.example.trend_project.apis.oauth.dto.UserDto;
import com.example.trend_project.apis.oauth.entity.MyKeywordEntity;
import com.example.trend_project.apis.oauth.entity.UserEntity;
import com.example.trend_project.apis.oauth.service.OAuthUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@SpringBootTest
@AutoConfigureMockMvc
class OAuthUserRepositoryTest {

    @Autowired
    private OAuthUserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OAuthUserService userService;
    @Autowired
    private MyKeywordRepository myKeywordRepository;


    @Rollback(value = false)
    @Test
    void saveUser() {
        var user = UserEntity.builder()
                .email("kwan09@naver.com")
                .name("관우")
                .build();
        userRepository.save(user);

//        for (int i = 0; i < 5; i++ ) {
//            var tmp = KeywordEntity.builder().user(user).build();
//            keywordRepository.save(tmp);
//        }
    }

    @Transactional
    @Rollback(value = false)
    @Test
    void addKeyword(){
        if (myKeywordRepository.findByUser(userRepository.findById(2L).orElseThrow()).size() < 5) {
            var user = userRepository.findById(2L).orElseThrow();
            var tmp = MyKeywordEntity.builder().user(user).keyword("수수깡").build();
            myKeywordRepository.save(tmp);
        } else System.out.println("더 이상 추가 할 수 없습니다");
    }

    @Transactional
    @Test
    void findUser(){
        var user = userRepository.findById(1L).orElseThrow();
        user.getKeywords().forEach(System.out::println);
    }

    @Test
    @Rollback(value = false)
    @Transactional
    void deleteKeyword(){
        var keywordsList = myKeywordRepository.findByUser(userRepository.findById(1L).orElseThrow());
        myKeywordRepository.deleteAll(keywordsList);

    }


    @Test
    @Rollback(value = false)
    @Transactional
    public void addMyKeyword() throws Exception {
        UserDto userDto = new UserDto();
        HttpSession session = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/index")
                .sessionAttr("user", userDto))
                .andReturn().getRequest().getSession();
        //유저 체킹을 해서 idx 값 도출
        var user = userRepository.findById(3L).orElseThrow();
        var keyword = "꽁치";

        //한 유저 당 들어갈 수 있는 키워드는 5개
        if (user.getKeywords().size() < 5) {
            var myKeyword = myKeywordRepository.save(MyKeywordEntity.builder()
                    .user(user)
                    .keyword(keyword)
                    .build());
//            user.addKeywords(myKeyword);

            //MyKeywordEntity > MyKeywordDto로 변경
            MyKeywordDto myKeywordDto = MyKeywordDto.builder()
                    .idx(myKeyword.getIdx())
                    .keyword(keyword)
                    .createAt(myKeyword.getCreatedAt())
                    .build();
            //UserEntity > UserDto로 변경
            userDto = userService.entityToDto(user);
            userDto.addKeyword(myKeywordDto.getKeyword());

            session.setAttribute("user", userDto);
        }

        userDto.getKeywords().forEach(System.out::println);
    }

}