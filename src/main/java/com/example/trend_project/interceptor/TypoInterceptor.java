package com.example.trend_project.interceptor;

import com.example.trend_project.apis.api.naver_typo.TypoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class TypoInterceptor implements HandlerInterceptor {

    private final TypoService typoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);

        //naver 오타 변환 api
        //요청 쿼리 문자열이나 폼 데이터에서 파라미터 값 가져오기
        var keyword = requestWrapper.getParameter("keyword");

        if (requestWrapper.getMethod().equals("GET")) {
            if (!typoService.getTypoTrans(keyword).getErrata().isEmpty()) {
                keyword = typoService.getTypoTrans(keyword).getErrata();
            }
        }

        request.setAttribute("keyword", keyword);

        return true;
    }
}
