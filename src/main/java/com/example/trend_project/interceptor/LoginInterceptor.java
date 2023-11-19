package com.example.trend_project.interceptor;

import com.example.trend_project.apis.oauth.dto.MyKeywordDto;
import com.example.trend_project.apis.oauth.dto.UserDto;
import com.example.trend_project.apis.oauth.service.MyKeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);

        //로그인 여부 확인
        var session = requestWrapper.getSession();
        var user = session.getAttribute("user");

        log.info("Session user = {}", user);

        if (user == null) {
            if (!response.isCommitted()) {
                alert(response);
            }
            return false;
        } else {
            return true;
        }
    }

    public void alert(HttpServletResponse response) {
        try {
            String message = "로그인 후 이용해주세요.";

            response.setContentType("text/html; charset=utf-8");
            PrintWriter w = response.getWriter();

            w.write("<script>");
            w.write("alert('" + message + "');"); // 경고 메시지 표시
            w.write("window.location.href = '/login';"); // 페이지 리디렉션
            w.write("</script>");

            w.flush();
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
