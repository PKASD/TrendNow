package com.example.trend_project.apis.oauth.controller;

import com.example.trend_project.apis.oauth.dto.MyKeywordDto;
import com.example.trend_project.apis.oauth.dto.UserDto;
import com.example.trend_project.apis.oauth.dto.UserUpdateDto;
import com.example.trend_project.apis.oauth.repository.OAuthUserRepository;
import com.example.trend_project.apis.oauth.service.MyKeywordService;
import com.example.trend_project.apis.oauth.service.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/modify")
@RequiredArgsConstructor
@Slf4j
public class UserInfoController {

    private final MyKeywordService keywordService;
    private final OAuthUserRepository userRepository;
    private final OAuthLoginService loginService;

    @GetMapping("/user-info")
    public String modifying(Model model, HttpSession session) {
        var user = session.getAttribute("user");
        model.addAttribute("user", user);
        return "my/information";
    }

    @Transactional
    @PostMapping(path = "/add")
    public ResponseEntity addKeyword(@RequestBody Map<String, String> requestBody, HttpSession session) {
        var user = (UserDto) session.getAttribute("user");
        String keyword = requestBody.get("keyword");

        if (keyword != null && !keyword.trim().isEmpty()) {
            MyKeywordDto dto = new MyKeywordDto();
            dto.setKeyword(keyword);

            keywordService.addMyKeyword(dto, user, keyword);
            loginService.updateUserKeywords(user, session);

            log.info("저장된 키워드 = {}", user.getKeywords());
            log.info("addMyKeyword = {}", keyword);

            return ResponseEntity.ok("키워드 추가 성공!");
        } else {
            return ResponseEntity.ok("키워드 추가 실패!");
        }
    }

    @DeleteMapping("/delete/{keywordIndex}")
    public ResponseEntity deleteKeyword(@PathVariable int keywordIndex, HttpSession session) {
        var user = (UserDto) session.getAttribute("user");
        log.info("userDto={}", user);

        if (user != null) {
            boolean success = keywordService.deleteKeyword(user, keywordIndex);
            if (success) {
                log.info("키워드 삭제 성공: keywordIndex={}", keywordIndex);
                return ResponseEntity.ok("키워드 삭제 성공");
            } else {
                log.info("키워드 삭제 실패: keywordIndex={}", keywordIndex);
                return ResponseEntity.badRequest().body("키워드 삭제 실패");
            }
        } else {
            log.info("사용자 미로그인");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 미로그인");
        }
    }

    @Transactional
    @PostMapping(path = "/gender-age")
    public String modifyUserInfo(@ModelAttribute UserUpdateDto updateDto, HttpSession session, Model model) {
        try {
            var user = (UserDto) session.getAttribute("user");

            var userUpdate = userRepository.findById(user.getIdx()).orElseThrow();

            userUpdate.setAges(updateDto.getAges());
            userUpdate.setGender(updateDto.getGender());

            userRepository.save(userUpdate);

            model.addAttribute("message", "정보 수정이 완료되었습니다.");
            model.addAttribute("user", user);

            return "my/information";
        } catch (Exception e) {
            e.printStackTrace();
            return "error-page";
        }
    }

    @ResponseBody
    @GetMapping(path = "/list")
    public List<MyKeywordDto> myKeywordList(HttpSession session){
        var user = (UserDto)session.getAttribute("user");
        return keywordService.getKeywordByUserId(user.getIdx());
    }

}
